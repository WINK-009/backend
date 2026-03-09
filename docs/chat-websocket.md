# Chat WebSocket Guide

## 1) Endpoint
- SockJS endpoint: `/ws-chat`
- STOMP app destination prefix: `/app`
- STOMP broker topic prefix: `/topic`

## 2) Channels
- Send (publish): `/app/chat/rooms/{chatRoomId}/messages`
- Subscribe: `/topic/chat/rooms/{chatRoomId}`

## 3) CONNECT header (required)
- Header key: `Authorization`
- Header value format: `Bearer <accessToken>`

If the header is missing or token is invalid, CONNECT is rejected.

## 4) Send body
```json
{
  "content": "¾È³çÇÏ¼¼¿ä"
}
```

`senderId` and `senderNickname` are not accepted from client anymore.
Sender is resolved from authenticated principal.

## 5) Receive body example
```json
{
  "messageId": 101,
  "senderId": 3,
  "senderNickname": "Ã¶¼ö",
  "content": "¾È³çÇÏ¼¼¿ä",
  "sentAt": "2026-03-09T10:30:00"
}
```

## 6) Frontend sample (stompjs + sockjs)
```javascript
import SockJS from 'sockjs-client';
import { Client } from '@stomp/stompjs';

const token = localStorage.getItem('accessToken');
const chatRoomId = 1;

const client = new Client({
  webSocketFactory: () => new SockJS('http://localhost:8080/ws-chat'),
  connectHeaders: {
    Authorization: `Bearer ${token}`,
  },
  reconnectDelay: 5000,
  onConnect: () => {
    client.subscribe(`/topic/chat/rooms/${chatRoomId}`, (frame) => {
      const message = JSON.parse(frame.body);
      console.log('received:', message);
    });

    client.publish({
      destination: `/app/chat/rooms/${chatRoomId}/messages`,
      body: JSON.stringify({ content: '¾È³çÇÏ¼¼¿ä' }),
    });
  },
  onStompError: (frame) => {
    console.error('STOMP error:', frame.headers['message'], frame.body);
  },
  onWebSocketError: (event) => {
    console.error('WebSocket error:', event);
  },
});

client.activate();
```

## 7) Quick test checklist
1. Access token is valid and not expired.
2. CONNECT contains `Authorization: Bearer <token>`.
3. Subscribe first, then publish.
4. Publish destination is `/app/...` and subscribe destination is `/topic/...`.
5. Same `chatRoomId` is used for publish/subscribe.
