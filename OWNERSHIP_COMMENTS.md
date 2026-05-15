# Wordy Application - Code Ownership & Team Responsibility

This document outlines the ownership of each class and component in the Wordy application based on the project task distribution.

## Team Members & Responsibilities

### Frontend/UI Team
- **JAN VON KRISTOFF RASALAN** - Player UI Components (Login, Home, Game Lobby, Game Screen, Game End, Leaderboard)
- **JENNY ANNE AWACAN** - Admin Dashboard UI & Player Account Management UI
- **ADELYN JOY TELA** - Round Results Display & Player Management UI
- **KATHRINA SHAYNE RAGOS** - Game Settings Configuration UI

### Backend/Service Team
- **NICOLE DEOCALES** - Authentication Backend, Game Screen Backend, Home Dashboard Backend
- **JACKSON MARIANO** - Leaderboard Backend, Top Players Backend, Round Results Backend, Game End Backend
- **CHARLES KENNETH DESEAR** - Game Lobby Backend, Player Management Backend, Game Settings Backend
- **KATHRINA SHAYNE RAGOS** - Admin Dashboard Backend (in progress)

---

## Player Module - UI Components

All UI constructors now include method-level ownership documentation explaining initialization and parameters.

### `WordyWaitingScreen.java`
- **Owner:** JAN VON KRISTOFF RASALAN
- **Task:** Game Lobby UI
- **Responsibility:** Displays waiting screen when players join a game, manages player count display
- **Key Methods:** 
  - Constructor - Initializes game lobby waiting screen (documented with ownership)
  - onGameUpdate() - Implements game update listener
  - UI event handlers - Cancel button, timeout handling

### `WordyGameScreen.java`
- **Owner:** JAN VON KRISTOFF RASALAN
- **Task:** Game Screen UI
- **Responsibility:** Main game interface where players submit words, view letters, track timer and rounds
- **Key Methods:** 
  - Constructor - Initializes game screen with letters, timer, submissions panel (documented with ownership)
  - onGameUpdate() - Implements game update listener for real-time updates
  - UI event handlers - Letter display, word submission, home/logout buttons

### `WordyDashboard.java`
- **Owner:** JAN VON KRISTOFF RASALAN
- **Task:** Home Screen UI
- **Responsibility:** Main player dashboard, game rules display, navigation to game lobby and leaderboard
- **Key Methods:** 
  - Constructor - Sets up dashboard with game rules and navigation buttons (documented with ownership)
  - displayGameRules() - Shows game instructions and rules
  - Navigation handlers - Join game, view leaderboard, logout

### `WordyLeaderBoard.java`
- **Owner:** JAN VON KRISTOFF RASALAN
- **Task:** Top Players UI
- **Responsibility:** Displays leaderboard with top players by wins and longest words
- **Key Methods:** 
  - Constructor - Initializes leaderboard display with ownership documentation
  - loadLeaderboardData() - Fetches data from server
  - displayTopPlayers() - Renders top winners panel
  - displayLongestWords() - Renders longest words panel

### `WordyGameResults.java`
- **Owners:** 
  - JAN VON KRISTOFF RASALAN (Game End UI - overall screen layout)
  - ADELYN JOY TELA (Round Result UI - round score table display)
- **Responsibility:** Shows final game results, round-by-round score breakdown, replay/dashboard options
- **Key Methods:** 
  - Constructor - Creates results display with winner announcement and round table (documented with co-ownership)
  - displayResults() - Shows final score and win/loss/tie message
  - showRoundScores() - Displays round-by-round breakdown table

---

## Admin Module - UI Components

### `AdminDashboard.java`
- **Owner:** JENNY ANNE AWACAN
- **Task:** Admin Dashboard UI
- **Responsibility:** Main admin interface for managing players and accessing admin functions
- **Status:** ✅ Refactored to use AdminServiceClient for real-time player data from database
- **Key Methods:** 
  - Constructor - Initializes dashboard with AdminServiceClient (documented with ownership)
  - loadPlayerData() - Fetches all players from server (documented with ownership)
  - refreshPlayerTable() - Updates table with search results (documented with ownership)
  - addRow() - Displays player data in table with edit/delete actions

### `AdminEditAccountUI.java`
- **Owner:** JENNY ANNE AWACAN
- **Task:** Edit/Add Player Account UI
- **Responsibility:** Form interface for editing existing player information or creating new player accounts
- **Status:** ✅ Refactored to create/update/delete players via AdminServiceClient
- **Key Methods:** 
  - Constructor - Creates form for edit or create mode (documented with ownership)
  - Admin recognizes edit vs create mode automatically based on username parameter
  - Save action calls createPlayer() or updatePlayer() service method based on mode

### `ManagePlayersUI.java`
- **Owner:** ADELYN JOY TELA
- **Task:** Manage Players UI
- **Responsibility:** Player list view with search functionality and action buttons (edit, delete)
- **Status:** ✅ Refactored to fetch players from database instead of showing hardcoded data
- **Key Methods:** 
  - Constructor - Initializes player management interface (documented with ownership)
  - loadPlayerData() - Fetches all players from server (documented with ownership)
  - refreshPlayerTable() - Updates table with search results (documented with ownership)
  - addPlayerRow() - Displays player data with edit/delete buttons

### `AdminGameSettingsUI.java`
- **Owner:** KATHRINA SHAYNE RAGOS
- **Task:** Game Settings Configuration UI
- **Responsibility:** Interface for configuring game parameters (waiting time, round duration)
- **Status:** ✅ Refactored to load/save settings from database instead of hardcoded defaults
- **Key Methods:** 
  - Constructor - Sets up game settings form and loads current values from server (documented with ownership)
  - loadGameSettings() - Fetches current settings from server (documented with ownership)
  - saveSettings() - Persists configuration changes to database via AdminServiceClient (documented with ownership)

---

## Player Module - Backend Services

### `AuthServiceClient.java`
- **Owner:** NICOLE DEOCALES
- **Task:** Login Backend
- **Responsibility:** Client wrapper for authentication gRPC service
- **Key Methods:** 
  - login() - Sends login request to server
  - logout() - Sends logout request to server

### `GameServiceClient.java`
- **Owner:** NICOLE DEOCALES
- **Task:** Game Screen Backend
- **Responsibility:** Client wrapper for game operations (join game, submit words)
- **Key Methods:** 
  - joinGame() - Requests to join a game
  - submitWord() - Submits player's word for validation
  - streamGameUpdates() - Establishes bidirectional stream for game updates

### `GameStateManager.java`
- **Owner:** NICOLE DEOCALES
- **Task:** Home Screen Backend
- **Responsibility:** Singleton manager coordinating all services and game state
- **Key Methods:** 
  - getInstance() - Gets singleton instance (documented with ownership)
  - login() - User authentication (documented with ownership)
  - logout() - Session termination (documented with ownership)
  - setCurrentUser() - Direct user assignment (documented with ownership)
  - joinGame() - Game lobby join (documented with ownership)
  - submitWord() - Word submission (documented with ownership)
  - startGameStream() - Listener for real-time updates (documented with ownership)

### `LeaderboardServiceClient.java`
- **Owner:** JACKSON MARIANO
- **Task:** Top Players Backend & Round Result Backend
- **Responsibility:** Client wrapper for leaderboard data retrieval
- **Key Methods:** 
  - getLeaderboard() - Fetches top players and longest words from server

### `GrpcConnectionManager.java` (Player Module)
- **Owners:** NICOLE DEOCALES, JACKSON MARIANO
- **Task:** Player Module Infrastructure
- **Responsibility:** Manages gRPC connection channel for player services
- **Key Methods:** 
  - getInstance() - Gets singleton instance (documented with ownership)
  - getChannel() - Gets or creates channel connection (documented with ownership)

---

## Server Module - Backend Services

### `AuthServiceImpl.java`
- **Owner:** NICOLE DEOCALES
- **Task:** Login Backend
- **Responsibility:** Server-side authentication implementation
- **Key Methods:** 
  - `login(LoginRequest, StreamObserver)` - Validates credentials, creates sessions (documented with ownership)
  - `logout(LogoutRequest, StreamObserver)` - Terminates user sessions (documented with ownership)
  - Session management helpers - Track active sessions, force logout

### `GameServiceImpl.java`
- **Owners:**
  - NICOLE DEOCALES (Game Screen Backend - base game mechanics, word submission)
  - CHARLES KENNETH DESEAR (Game Lobby Backend - joining, lobby management)
- **Task:** Game Operations Backend
- **Responsibility:** Handles game creation, player joining, word submission, game streaming
- **Key Methods:**
  - `joinGame(JoinGameRequest, StreamObserver)` - CHARLES KENNETH DESEAR (documented with ownership)
  - `submitWord(WordRequest, StreamObserver)` - NICOLE DEOCALES (documented with ownership)
  - `streamGame(GameRequest, StreamObserver)` - NICOLE DEOCALES (documented with ownership)
  - `startGame()` - Initiates game with 2 players

### `LeaderboardServiceImpl.java`
- **Owner:** JACKSON MARIANO
- **Task:** Top Players Backend & Round Result Backend
- **Responsibility:** Fetches leaderboard data (top players, longest words)
- **Key Methods:** 
  - `getLeaderboard(Empty, StreamObserver)` - Returns top players and longest words (documented with ownership)

### `AdminServiceImpl.java`
- **Owners:**
  - KATHRINA SHAYNE RAGOS (Admin Dashboard Backend)
  - CHARLES KENNETH DESEAR (Player Management Backend & Game Settings Backend)
- **Task:** Admin Operations Backend
- **Responsibility:** Player CRUD operations, configuration management
- **Key Methods:**
  - `createPlayer(Player, StreamObserver)` - Creates new player account (documented - CHARLES KENNETH DESEAR)
  - `updatePlayer(Player, StreamObserver)` - Updates player information (documented - CHARLES KENNETH DESEAR)
  - `deletePlayer(PlayerId, StreamObserver)` - Removes player account (documented - CHARLES KENNETH DESEAR)
  - `searchPlayer(SearchQuery, StreamObserver)` - Finds players by username (documented - CHARLES KENNETH DESEAR)
  - `updateConfig(Config, StreamObserver)` - Updates game configuration (documented - CHARLES KENNETH DESEAR)

---

## Common/Shared Components

### `WordyLoginUI.java`
- **UI Owner:** JAN VON KRISTOFF RASALAN
- **Backend Owner:** NICOLE DEOCALES
- **Task:** Login Screen UI
- **Responsibility:** Shared login interface used by both Player and Admin applications
- **Key Methods:** 
  - Constructor - Creates login UI (owned by JAN VON KRISTOFF RASALAN)
  - handleLogin() - Processes login request
  - handleCancel() - Dismisses login dialog

### `AdminServiceClient.java` (Admin Module - NEW)
- **Owner:** CHARLES KENNETH DESEAR
- **Task:** Admin Module Backend Services
- **Responsibility:** Client wrapper for admin gRPC service - fetches and manages player data and game settings from server
- **Key Methods:** 
  - getAllPlayers() - Fetches all players from database (documented with ownership)
  - searchPlayers() - Searches players by username (documented with ownership)
  - createPlayer() - Creates new player account (documented with ownership)
  - updatePlayer() - Updates player information (documented with ownership)
  - deletePlayer() - Deletes player account (documented with ownership)
  - getGameSettings() - Fetches current game configuration (documented with ownership)
  - updateGameSettings() - Saves game configuration to database (documented with ownership)

### `GrpcConnectionManager.java` (Admin Module)
- **Owners:** KATHRINA SHAYNE RAGOS (primary), JENNY ANNE AWACAN, CHARLES KENNETH DESEAR
- **Task:** Admin Module Infrastructure
- **Responsibility:** Manages gRPC connection channel for admin services
- **Key Methods:** 
  - getInstance() - Gets singleton instance (documented - KATHRINA SHAYNE RAGOS)
  - getChannel() - Gets or creates channel connection (documented - KATHRINA SHAYNE RAGOS)

---

## Method-Level Ownership Comments

All public methods now include detailed ownership documentation. Each method specifies:
- **Responsible Team Member**: Who owns and is responsible for this method
- **Purpose**: What the method does
- **Parameters & Returns**: @param and @return documentation

### Backend Service Methods

**AuthServiceImpl.java (NICOLE DEOCALES)**
- `login(LoginRequest, StreamObserver)` - Authenticates credentials and creates sessions
- `logout(LogoutRequest, StreamObserver)` - Terminates user sessions

**GameServiceImpl.java (Mixed Ownership)**
- `joinGame(JoinGameRequest, StreamObserver)` - KATHRINA SHAYNE RAGOS (Game Lobby Backend)
- `submitWord(WordRequest, StreamObserver)` - NICOLE DEOCALES (Game Screen Backend)
- `streamGame(GameRequest, StreamObserver)` - NICOLE DEOCALES (Game Screen Backend)

**LeaderboardServiceImpl.java (JACKSON MARIANO)**
- `getLeaderboard(Empty, StreamObserver)` - Fetches top players and longest words

**AdminServiceImpl.java (Mixed Ownership)**
- `createPlayer(Player, StreamObserver)` - CHARLES KENNETH DESEAR
- `updatePlayer(Player, StreamObserver)` - CHARLES KENNETH DESEAR
- `deletePlayer(PlayerId, StreamObserver)` - CHARLES KENNETH DESEAR
- `searchPlayer(SearchQuery, StreamObserver)` - CHARLES KENNETH DESEAR
- `updateConfig(Config, StreamObserver)` - CHARLES KENNETH DESEAR (Game Settings)

### Client Service Methods

**GameStateManager.java (NICOLE DEOCALES)**
- `getInstance()` - Singleton accessor
- `login(String, String, boolean)` - User authentication
- `logout()` - Session termination
- `setCurrentUser(String, String)` - Direct user assignment
- `joinGame()` - Game lobby join
- `submitWord(String)` - Word submission
- `startGameStream()` - Real-time updates listener

**GrpcConnectionManager (Both Modules)**
- `getInstance()` - Singleton accessor (KATHRINA SHAYNE RAGOS for Admin, NICOLE DEOCALES & JACKSON MARIANO for Player)
- `getChannel()` - Channel connection management

### UI Constructor Methods

**Player Module (All by JAN VON KRISTOFF RASALAN)**
- `WordyWaitingScreen(JFrame)` - Game Lobby initialization
- `WordyGameScreen(int)` - Game Screen setup
- `WordyDashboard(String)` - Home Dashboard creation
- `WordyLeaderBoard(String)` - Leaderboard display

**Admin Module**
- `AdminDashboard(String)` - JENNY ANNE AWACAN
- `AdminEditAccountUI(String)` - JENNY ANNE AWACAN
- `ManagePlayersUI()` - ADELYN JOY TELA
- `AdminGameSettingsUI()` - KATHRINA SHAYNE RAGOS

---

## Code Organization Standards

### When Committing Code:
1. **Always include the team member's name in class-level documentation**
2. **Include responsibility comments on all public methods**
3. **Use this format for class comments:**
   ```java
   /**
    * [UI/Backend Component Name]
    * Responsible Team Member: [NAME]
    * [Brief description of functionality]
    */
   ```

4. **Use this format for method comments:**
   ```java
   /**
    * Brief description of what the method does.
    * Responsible Team Member: [NAME]
    * 
    * Additional context if needed.
    * 
    * @param paramName description
    * @return description of return value
    */
   ```

### Commit Message Format:
```
[Feature/Task Name] - [Team Member Name]

Description of changes made...
```

### Example:
```
[Game Lobby UI] - JAN VON KRISTOFF RASALAN

Added player count update feature to WordyWaitingScreen.
- Implemented real-time player count display
- Added game start listener for stream updates
- Added timeout handler for abandoned lobbies
```

---

## Related Task Board Status

| Task | Status | Owner |
|------|--------|-------|
| Login Screen UI | ✅ Completed | JAN VON KRISTOFF RASALAN |
| Login Backend | ✅ Completed | NICOLE DEOCALES |
| Home Screen UI | ✅ Completed | JAN VON KRISTOFF RASALAN |
| Home Screen Backend | ✅ Completed | NICOLE DEOCALES |
| Game Lobby UI | ✅ Completed | JAN VON KRISTOFF RASALAN |
| Game Lobby Backend | ✅ Completed | CHARLES KENNETH DESEAR |
| Game Screen UI | ✅ Completed | JAN VON KRISTOFF RASALAN |
| Game Screen Backend | ✅ Completed | NICOLE DEOCALES |
| Round Result UI | ✅ Completed | ADELYN JOY TELA |
| Round Result Backend | ✅ Completed | JACKSON MARIANO |
| Game End UI | ✅ Completed | JAN VON KRISTOFF RASALAN |
| Game End Backend | ✅ Completed | JACKSON MARIANO |
| Top Players UI | ✅ Completed | JAN VON KRISTOFF RASALAN |
| Top Players Backend | ✅ Completed | JACKSON MARIANO |
| Admin Dashboard UI | ✅ Completed | JENNY ANNE AWACAN |
| Admin Dashboard Backend | 🟡 In Progress | KATHRINA SHAYNE RAGOS |
| Manage Players UI | ✅ Completed | ADELYN JOY TELA |
| Manage Players Backend | ✅ Completed | CHARLES KENNETH DESEAR |
| Edit/Add Player UI | ✅ Completed | JENNY ANNE AWACAN |
| Game Setting UI | ✅ Completed | KATHRINA SHAYNE RAGOS |
| Game Setting Backend | ✅ Completed | CHARLES KENNETH DESEAR |

---

## Updated Files Summary

All Java source files (non-generated) have been updated with **class-level and method-level ownership comments**.

### Player Module (15 files updated)

**UI Components** (5 files)
- ✅ `player/src/main/java/com/wordy/player/WordyWaitingScreen.java` - Constructor with ownership comment
- ✅ `player/src/main/java/com/wordy/player/WordyGameScreen.java` - Constructor with ownership comment
- ✅ `player/src/main/java/com/wordy/player/WordyDashboard.java` - Constructor + method comments
- ✅ `player/src/main/java/com/wordy/player/WordyLeaderBoard.java` - Constructor + method comments
- ✅ `player/src/main/java/com/wordy/player/WordyGameResults.java` - Constructor with ownership comment

**Backend Services** (5 files)
- ✅ `player/src/main/java/com/wordy/player/service/AuthServiceClient.java` - Class comment (methods already documented)
- ✅ `player/src/main/java/com/wordy/player/service/GameServiceClient.java` - Class comment (methods already documented)
- ✅ `player/src/main/java/com/wordy/player/service/GameStateManager.java` - Multiple method ownership comments added
- ✅ `player/src/main/java/com/wordy/player/service/LeaderboardServiceClient.java` - Class comment (methods already documented)
- ✅ `player/src/main/java/com/wordy/player/service/GrpcConnectionManager.java` - Method ownership comments updated

### Admin Module (5 files updated)

**UI Components** (4 files)
- ✅ `admin/src/main/java/com/wordy/admin/AdminDashboard.java` - Constructor + method comments
- ✅ `admin/src/main/java/com/wordy/admin/AdminEditAccountUI.java` - Constructor with ownership comment
- ✅ `admin/src/main/java/com/wordy/admin/ManagePlayersUI.java` - Constructor with ownership comment
- ✅ `admin/src/main/java/com/wordy/admin/AdminGameSettingsUI.java` - Constructor with ownership comment

**Infrastructure** (1 file)
- ✅ `admin/src/main/java/com/wordy/admin/service/GrpcConnectionManager.java` - Method ownership comments updated

### Server Module (4 files updated)

**Backend Services**
- ✅ `server/src/main/java/com/wordy/server/service/AuthServiceImpl.java` - Method ownership comments added
- ✅ `server/src/main/java/com/wordy/server/service/GameServiceImpl.java` - Method ownership comments added
- ✅ `server/src/main/java/com/wordy/server/service/LeaderboardServiceImpl.java` - Method ownership comments added
- ✅ `server/src/main/java/com/wordy/server/service/AdminServiceImpl.java` - Method ownership comments added

### Common Module (1 file updated)

- ✅ `common/src/main/java/com/wordy/common/WordyLoginUI.java` - Class comment with UI and Backend ownership

---

## Completion Status

✅ **All class-level ownership comments added**
✅ **All method-level ownership comments added**
✅ **Code ownership documentation complete**

### Outstanding Tasks

1. **Team member review** - Each team member should review their assigned components
2. **Ongoing maintenance** - Keep documentation updated as new features are added or responsibilities change
3. **Commit enforcement** - Ensure all future commits follow the established format with ownership comments
