# Subscription Manager (サブスクリプション管理アプリ)

コンソール上でサブスクリプションサービスの登録・一覧表示・更新・削除を管理できるJavaアプリケーションです。データはローカルのCSVファイルに自動保存・永続化されます。

---

## 📋 主な機能

* **一覧表示**: 登録されているサブスクリプション情報（ID、名称、カテゴリ、請求日など）をコンソールに一覧表示
* **新規登録**: サービス名、カテゴリ、請求サイクル、金額、次回請求日、利用頻度を指定してCSVへ保存
* **データ更新**: 指定したIDの登録情報を対話形式で上書き更新
* **データ削除**: 指定したIDのサブスクリプション情報をCSVから削除
* **データ永続化**: 外部データベースを使わず、ローカルのCSVファイル（`subscriptions.csv`）でデータを管理

---

## 🛠 使用技術・環境

* **言語**: Java 17 以上 (またはお使いのバージョン)
* **ストレージ**: CSV (`subscriptions.csv`)
* **主要API**: 
  * `java.nio.file` (ファイル読み書き)
  * `java.time.LocalDate` (日付管理)
  * Enum (カテゴリ・請求サイクル・利用頻度の型安全な管理)

---

## 📂 プロジェクト構成

```text
src/
└── subscmanager/
    ├── model/               # データ構造・列挙型
    │   ├── Subscription.java
    │   ├── Category.java
    │   ├── BillingCycle.java
    │   └── UsageFrequency.java
    ├── repository/          # データ永続化 (CSV操作)
    │   └── SubscriptionRepository.java
    ├── survice/             # 料金計算の処理
    │   └── SubscriptionService.java
    ├── ui/                  # サブスクの登録や表示
    │   └── ConsoleView.java
    │   └── ConsoleController.java
    └── Main.java            # エントリーポイント (コンソールUI)
