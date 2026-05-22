# 💬 LocalChat

<div align="center">

![LocalChat Banner](https://img.shields.io/badge/LocalChat-v1.0.0-4F7FFF?style=for-the-badge&logo=chatbot&logoColor=white)
![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.6-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![React](https://img.shields.io/badge/React-18.3.1-61DAFB?style=for-the-badge&logo=react&logoColor=black)
![Vite](https://img.shields.io/badge/Vite-5.4.1-646CFF?style=for-the-badge&logo=vite&logoColor=white)
![WebSocket](https://img.shields.io/badge/WebSocket-STOMP-3ECF8E?style=for-the-badge&logo=socket.io&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**A self-contained, privacy-first real-time chat application for local networks.**  
No internet required. No database. No login. Just open and chat.

[Features](#-features) • [Demo](#-demo) • [Quick Start](#-quick-start) • [Architecture](#-architecture) • [API Docs](#-api-documentation) • [Contributing](#-contributing)

</div>

---

## 📌 About

LocalChat is a **LAN-only** real-time chat application built with **Java Spring Boot** and **React**. It identifies users automatically by their device's IP address — no signup, no login. All chat history is **automatically deleted** the moment the last user disconnects, making it ideal for private, ephemeral team communication.

> ✅ Works entirely on your local network — never touches the internet.  
> ✅ Zero data stored on disk — all messages live in memory only.  
> ✅ Auto-wipes history when the last user leaves.

---

## ✨ Features

| Feature | Description |
|---|---|
| ⚡ **Real-time messaging** | WebSocket + STOMP for instant message delivery |
| 🔍 **IP-based identity** | Users identified by LAN IP — zero registration |
| 🗑️ **Auto-delete history** | All messages wiped when last user disconnects |
| 👥 **Live user sidebar** | See who is currently online |
| ✍️ **Typing indicator** | See when others are composing a message |
| 🎨 **Polished dark UI** | Modern interface with color-coded users |
| 🔒 **LAN-only** | Completely unreachable from the internet |
| 💾 **Zero persistence** | No database, no disk writes, no logs |

---

## 🖥️ Demo

```
┌─────────────────────────────────────────────────────┐
│  localchat              LAN only   3 online    ● Connected │
├──────────────┬──────────────────────────────────────┤
│ Online — 3   │                                      │
│              │  → 172.25.79.17 joined               │
│ [42] 172.25  │  → 172.25.79.88 joined               │
│     .79.42   │                                      │
│     you      │  172.25.79.17    10:43               │
│              │  ┌─────────────────────────────────┐ │
│ [17] 172.25  │  │ Hey team! Anyone on a call?     │ │
│     .79.17   │  └─────────────────────────────────┘ │
│              │                                      │
│ [88] 172.25  │  You (172.25.79.42)          10:43  │
│     .79.88   │              ┌──────────────────────┐│
│              │              │ Not me, free to chat ││
│ ⚠ no history │              └──────────────────────┘│
│ Chat wiped   ├──────────────────────────────────────┤
│ on disconnect│  Message the team…            [Send] │
└──────────────┴──────────────────────────────────────┘
```

---

## 🚀 Quick Start

### Prerequisites

Make sure you have these installed:

```bash
java -version    # Java 17+
node --version   # Node.js 18+
npm --version    # npm 9+
```

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/localchat.git
cd localchat
```

### 2. Start the backend

```bash
# From the root of the project
./mvnw spring-boot:run
```

✅ You should see: `Tomcat started on port(s): 8080`

### 3. Start the frontend

```bash
cd frontend
npm install
npm run dev
```

✅ You should see: `Local: http://localhost:5173`

### 4. Open the app

Open your browser and go to:
```
http://localhost:5173
```

### 5. Let colleagues join

Find your machine's IP address:
```bash
# Windows
ipconfig

# Mac / Linux
ifconfig | grep "inet "
```

Share this URL with your team:
```
http://<your-ip>:5173
```

> **Note:** All devices must be on the same Wi-Fi or LAN network.

---

## 📁 Project Structure

```
localchat/
│
├── 📂 src/                              # Spring Boot Backend
│   └── main/java/com/localchat/
│       ├── 📂 config/
│       │   ├── SecurityConfig.java      # IP access control, CSRF config
│       │   └── WebSocketConfig.java     # STOMP broker, IP interceptor
│       ├── 📂 controller/
│       │   └── ChatController.java      # Message routing endpoints
│       ├── 📂 model/
│       │   ├── ChatMessage.java         # Message DTO (CHAT/JOIN/LEAVE/TYPING)
│       │   └── ChatUser.java            # User identity DTO
│       └── 📂 service/
│           ├── MessageStore.java        # Thread-safe in-memory store
│           └── SessionService.java      # Connect/disconnect lifecycle
│
├── 📂 src/main/resources/
│   └── application.properties          # Server config (port, binding)
│
├── pom.xml                             # Maven dependencies
│
└── 📂 frontend/                        # React + Vite Frontend
    ├── package.json
    ├── vite.config.js                  # Proxy config for backend
    └── 📂 src/
        ├── index.css                   # Global styles and design tokens
        ├── main.jsx                    # React entry point
        ├── App.jsx                     # Root component and layout
        ├── 📂 hooks/
        │   └── useWebSocket.js         # STOMP connection and state
        └── 📂 components/
            ├── MessageBubble.jsx       # Chat bubble renderer
            ├── UserList.jsx            # Online users sidebar
            └── InputBar.jsx            # Message compose + send
```

---

## 🏗️ Architecture

### System Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                         LOCAL NETWORK (LAN)                     │
│                                                                  │
│   ┌──────────────────┐          ┌──────────────────────────┐   │
│   │   Browser         │          │    Spring Boot Server     │   │
│   │   React + Vite    │◄────────►│    Java 17 + WebSocket   │   │
│   │   Port: 5173      │  STOMP   │    Port: 8080            │   │
│   │                   │ over WS  │                          │   │
│   │  ┌─────────────┐ │          │  ┌──────────────────┐   │   │
│   │  │useWebSocket │ │          │  │  ChatController   │   │   │
│   │  │  (hook)     │ │          │  │  /app/chat.send  │   │   │
│   │  └─────────────┘ │          │  │  /app/chat.join  │   │   │
│   │  ┌─────────────┐ │          │  └──────────────────┘   │   │
│   │  │ MessageList │ │          │  ┌──────────────────┐   │   │
│   │  │  UserList   │ │          │  │  MessageStore    │   │   │
│   │  │  InputBar   │ │          │  │  (in-memory)     │   │   │
│   └──────────────────┘          │  └──────────────────┘   │   │
│                                  │  ┌──────────────────┐   │   │
│                                  │  │ SessionService   │   │   │
│                                  │  │ (auto-delete)    │   │   │
│                                  │  └──────────────────┘   │   │
│                                  └──────────────────────────┘   │
└─────────────────────────────────────────────────────────────────┘
```

### Message Flow

```
User types message
       │
       ▼
InputBar.jsx — handleSend()
       │
       ▼
useWebSocket.js — client.publish('/app/chat.send')
       │
       ▼
Vite Proxy — forwards to :8080
       │
       ▼
ChatController.sendMessage()
  ├── Read IP from TCP session (server-side, tamper-proof)
  ├── Validate + sanitize content
  ├── Set timestamp
  └── MessageStore.saveMessage()
       │
       ▼
messaging.convertAndSend('/topic/public')
       │
       ▼
All connected clients receive frame
       │
       ▼
useWebSocket.js — setMessages(prev => [...prev, msg])
       │
       ▼
MessageBubble renders new chat bubble
```

### Privacy Flow (Auto-Delete)

```
User closes browser tab
       │
       ▼
Spring fires SessionDisconnectEvent (automatic)
       │
       ▼
SessionService.handleDisconnect()
  ├── Look up ChatUser by sessionId
  ├── MessageStore.removeUserAndCheckEmpty()
  │     ├── Remove user from activeUsers map
  │     └── If room empty → messages.clear() ✓ WIPED
  └── Broadcast LEAVE notice to remaining users
```

---

## 🔌 API Documentation

### WebSocket Endpoint

```
ws://<server-ip>:8080/ws
```

### STOMP Destinations (Client → Server)

| Destination | Payload | Description |
|---|---|---|
| `/app/chat.join` | `{ type: "JOIN" }` | Announce presence on connect |
| `/app/chat.send` | `{ type: "CHAT", content: "..." }` | Send a message (max 1000 chars) |
| `/app/chat.typing` | `{ type: "TYPING" }` | Notify others you're typing |

### STOMP Broadcasts (Server → Client)

| Topic | Payload Type | Triggered By |
|---|---|---|
| `/topic/public` | `ChatMessage (JOIN)` | User connects |
| `/topic/public` | `ChatMessage (CHAT)` | User sends message |
| `/topic/public` | `ChatMessage (LEAVE)` | User disconnects |
| `/topic/public` | `ChatMessage (TYPING)` | User is typing |

### REST Endpoints

| Method | Path | Response | Description |
|---|---|---|---|
| `GET` | `/api/messages` | `ChatMessage[]` | Current room history |
| `GET` | `/api/users` | `ChatUser[]` | Connected users list |

### ChatMessage Schema

```json
{
  "type":       "CHAT | JOIN | LEAVE | TYPING",
  "content":    "string (max 1000 chars)",
  "senderIp":   "string (set server-side — cannot be spoofed)",
  "senderName": "string (derived from IP e.g. User 42)",
  "timestamp":  "2026-04-24T10:30:00.000Z"
}
```

> ⚠️ `senderIp`, `senderName`, and `timestamp` are always **overwritten server-side**. Client-supplied values for these fields are ignored.

---

## ⚙️ Configuration

### Backend — `application.properties`

```properties
server.port=8080
server.address=0.0.0.0          # Bind to all LAN interfaces
spring.application.name=localchat
spring.autoconfigure.exclude=\
  org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
logging.level.com.localchat=DEBUG
```

### Frontend — `vite.config.js`

```js
export default defineConfig({
  server: {
    host: '0.0.0.0',   // Serve to all LAN devices
    port: 5173,
    proxy: {
      '/api': { target: 'http://localhost:8080', changeOrigin: true },
      '/ws':  { target: 'http://localhost:8080', changeOrigin: true, ws: true },
    },
  },
  define: { global: 'globalThis' },  // SockJS compatibility fix
})
```

---

## 🔒 Security

### Built-in Controls

- **IP captured server-side** — identity cannot be spoofed by clients
- **No persistence** — zero disk writes, nothing to steal after shutdown
- **LAN-only binding** — unreachable from the internet
- **Input sanitization** — messages trimmed and capped at 1000 characters
- **Auto-delete** — history wiped when the last user disconnects

### Optional Hardening

<details>
<summary><strong>IP Allowlist</strong> — restrict to specific devices</summary>

```java
// SecurityConfig.java
private static final List<String> ALLOWED_IPS = List.of(
    "172.25.79.42",   // your machine
    "172.25.79.17",   // colleague 1
    "127.0.0.1"
);
```
</details>

<details>
<summary><strong>Join Password</strong> — shared secret to enter the room</summary>

```java
// ChatController.java
private static final String JOIN_PASSWORD = "yourpassword";

@MessageMapping("/chat.join")
public void joinUser(@Payload ChatMessage message, ...) {
    if (!JOIN_PASSWORD.equals(message.getContent())) return;
    // proceed...
}
```
</details>

<details>
<summary><strong>Close ports when done</strong> — Windows firewall</summary>

```bat
netsh advfirewall firewall delete rule name="LocalChat Vite"
netsh advfirewall firewall delete rule name="LocalChat Spring"
```
</details>

---

## 🐛 Troubleshooting

| Error | Cause | Fix |
|---|---|---|
| `global is not defined` | SockJS/Node.js conflict with Vite | Add `define: { global: 'globalThis' }` to `vite.config.js` |
| `403 Forbidden on /ws/*` | Spring Security blocking SockJS | Ensure `csrf().disable()` in `SecurityConfig` |
| `WebSocket connection failed` | Wrong origin pattern | Add your IP range to `setAllowedOriginPatterns()` |
| Site unreachable from other device | Firewall blocking port | Run `netsh` command to open ports 5173 and 8080 |
| Multiple default exports error | Old + new code both in file | Replace entire file — don't append below old code |
| Green dot never appears | Backend not running | Confirm Spring Boot shows `port 8080` in logs |

---

## 🤝 Contributing

Contributions are welcome! Here's how to get started:

### Getting Started

1. **Fork** the repository on GitHub
2. **Clone** your fork locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/localchat.git
   ```
3. **Create a branch** for your feature:
   ```bash
   git checkout -b feature/your-feature-name
   ```
4. **Make your changes** following the code style guide below
5. **Test** your changes locally
6. **Commit** with a clear message:
   ```bash
   git commit -m "feat: add private messaging between users"
   ```
7. **Push** to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```
8. Open a **Pull Request** on GitHub

### Commit Message Convention

We follow [Conventional Commits](https://www.conventionalcommits.org/):

| Prefix | Use For |
|---|---|
| `feat:` | New feature |
| `fix:` | Bug fix |
| `docs:` | Documentation changes |
| `style:` | CSS/formatting changes |
| `refactor:` | Code restructuring |
| `test:` | Adding tests |
| `chore:` | Build or tooling changes |

### Code Style

**Backend (Java):**
- Use constructor injection over `@Autowired`
- Use Lombok annotations (`@Data`, `@Builder`, `@RequiredArgsConstructor`)
- Add comments explaining *why*, not just *what*
- All IP reads must be server-side from session attributes only

**Frontend (React):**
- Use functional components with hooks only
- Extract reusable logic into custom hooks in `src/hooks/`
- CSS classes only — no inline styles except for dynamic values
- One `export default` per file

### 🌟 Feature Ideas (Good First Issues)

- [ ] **Custom display names** — let users choose a nickname
- [ ] **Sound notifications** — beep on new message
- [ ] **Multiple rooms** — dropdown to switch between channels
- [ ] **Private messaging** — click a user in sidebar to DM them
- [ ] **Message reactions** — emoji reactions on hover
- [ ] **File sharing** — send images over the LAN
- [ ] **Dark/light theme toggle** — add a theme switcher
- [ ] **Message search** — Ctrl+F to search within session
- [ ] **User avatars** — custom avatar upload per session
- [ ] **HTTPS/WSS support** — self-signed cert for encrypted LAN traffic

---

## 📋 Roadmap

### v1.0 (Current)
- [x] Real-time messaging via WebSocket + STOMP
- [x] IP-based user identification
- [x] Auto-delete history on disconnect
- [x] Typing indicator
- [x] Online user sidebar
- [x] LAN-only access control

### v1.1 (Planned)
- [ ] Custom display names
- [ ] Sound notifications
- [ ] Message reactions

### v2.0 (Future)
- [ ] Multiple chat rooms
- [ ] Private messaging
- [ ] File/image sharing
- [ ] HTTPS support

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

```
MIT License — free to use, modify, and distribute.
Attribution appreciated but not required.
```

---

## 🙌 Acknowledgements

Built with:
- [Spring Boot](https://spring.io/projects/spring-boot) — Java application framework
- [React](https://react.dev/) — UI library
- [Vite](https://vitejs.dev/) — Frontend build tool
- [STOMP.js](https://stomp-js.github.io/) — WebSocket messaging
- [SockJS](https://github.com/sockjs/sockjs-client) — WebSocket fallback
- [Lombok](https://projectlombok.org/) — Java boilerplate reduction
- [DM Sans](https://fonts.google.com/specimen/DM+Sans) + [IBM Plex Mono](https://fonts.google.com/specimen/IBM+Plex+Mono) — Typography

---

<div align="center">

Made with ❤️ for teams who value privacy

⭐ **Star this repo if it helped you!** ⭐

</div>
