# 채팅 웹소켓 가이드

## 1) 엔드포인트
- SockJS 엔드포인트: `/ws-chat`
- STOMP 앱 목적지 prefix: `/app`
- STOMP 브로커 토픽 prefix: `/topic`

## 2) 채널
- 메시지 전송(Publish): `/app/chat/rooms/{chatRoomId}/messages`
- 메시지 구독(Subscribe): `/topic/chat/rooms/{chatRoomId}`

## 3) CONNECT 헤더 (필수)
- 헤더 키: `Authorization`
- 헤더 값 형식: `Bearer <accessToken>`

`Authorization` 헤더가 없거나 토큰이 유효하지 않으면 CONNECT 요청은 거절됩니다.

## 4) 전송 본문 예시
```json
{
  "content": "안녕하세요"
}
```

클라이언트에서 `senderId`, `senderNickname`은 전달하지 않습니다.
발신자 정보는 인증된 principal에서 서버가 추출합니다.

## 5) 수신 본문 예시
```json
{
  "messageId": 101,
  "senderId": 3,
  "senderNickname": "철수",
  "content": "안녕하세요",
  "sentAt": "2026-03-09T10:30:00"
}
```

## 6) 프론트 연동 예시 (stompjs + sockjs)
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
      console.log('수신 메시지:', message);
    });

    client.publish({
      destination: `/app/chat/rooms/${chatRoomId}/messages`,
      body: JSON.stringify({ content: '안녕하세요' }),
    });
  },
  onStompError: (frame) => {
    console.error('STOMP 오류:', frame.headers['message'], frame.body);
  },
  onWebSocketError: (event) => {
    console.error('WebSocket 오류:', event);
  },
});

client.activate();
```

## 7) 빠른 점검 체크리스트
1. 액세스 토큰이 유효하고 만료되지 않았는지 확인합니다.
2. CONNECT 헤더에 `Authorization: Bearer <token>`이 포함되어 있는지 확인합니다.
3. `subscribe` 이후 `publish` 순서로 호출합니다.
4. 전송 경로는 `/app/...`, 구독 경로는 `/topic/...`를 사용합니다.
5. 전송/구독에 동일한 `chatRoomId`를 사용합니다.
