# Client- Server Connection

A multi-threaded client-server application written in Java that demonstrates TCP socket communication for multiplayer games such as Chess.

The project uses a thread-per-client model, allowing the server to manage multiple client connections simultaneously. Although the current implementation provides the networking infrastructure, it can be extended to support complete game logic, matchmaking, or other multiplayer board games.

###  Possible implementations:
- Chess game logic
- Move validation
- GUI (JavaFX or Swing)
- Matchmaking
- User authentication
- Chat system
---

## Features

- Multi-threaded server architecture
- TCP socket communication
- Text-based client interface (TUI)
- Modular networking classes
- Simple communication protocol
- Cross-platform (Windows, Linux, and macOS)
- Easy to extend for multiplayer games

### Future explansion:
- Graphical User Interface (GUI)

---

## Project Structure

```
ClientServerConnection/
│
├── src/
│   ├── client/
│   │
│   ├── network/
│   │
│   ├── Protocol/
│   │
│   └── Server/
│
└── logs.txt
```
---

# Requirements

- Java Development Kit (JDK) 17 or later

Verify your Java installation:

```bash
java -version
javac -version
```

---

# Compiling

## Windows

Create an output directory:

```cmd
mkdir bin
```

Compile all source files:

```cmd
javac -d bin src\client\*.java src\network\*.java src\Protocol\*.java src\Server\*.java
```

---

## Linux

```bash
mkdir -p bin

find src -name "*.java" | xargs javac -d bin
```

---

## macOS

```bash
mkdir -p bin

find src -name "*.java" | xargs javac -d bin
```

---

# Running

The server must be started before clients connect.

## Start the Server

### Windows

```cmd
java -cp bin Server.ChatServer
```

### Linux/macOS

```bash
java -cp bin Server.ChatServer
```

Expected output:

```
Server started...
Waiting for clients...
```

---

## Start a Client

Open another terminal.

### Windows

```cmd
java -cp bin client.ChatClientTUI
```

or, if applicable,

```cmd
java -cp bin client.ChatClient
```

### Linux/macOS

```bash
java -cp bin client.ChatClientTUI
```

or

```bash
java -cp bin client.ChatClient
```

Repeat this command in additional terminals to simulate multiple players.

