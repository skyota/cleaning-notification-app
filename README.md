# 掃除通知アプリ (Cleaning Notification App)

「ついうっかり掃除を忘れてしまう」を解決するための、自分専用の掃除管理・通知アプリです。
場所ごとの詳細な掃除手順（マニュアル）を管理し、適切なタイミングで LINE 通知を行います。

## 技術スタック
- Frontend: React, TypeScript, Vite
- Backend: Java, Spring Boot, Spring Data JPA
- Database: PostgreSQL
- Infrastructure: Docker, Docker Compose
- Notification: LINE Messaging API

## 設計
### データベース設計 (ER図)
- TASKS: 掃除場所、対象、頻度（日数）、手順、次回予定日
- TASK_HISTORIES: 掃除の実施記録（いつ、どのタスクを完了したか）

### API エンドポイント
- GET /tasks : タスク一覧の取得
- POST /tasks/{id}/complete : 掃除完了の記録と次回予定日の更新

## セットアップ方法
1. リポジトリをクローン
2. `.env` ファイルを作成し、DBの情報を設定
3. 以下のコマンドを実行
   ```bash
   docker compose up -d