# 7mi-TCT
ななみTCTのリポジトリ
ソースコードは自由に利用できますが、下記の許可されていること、禁止されていることについてはよく目を通してください。

# 許可されていること
- 個人利用
- ソースコードの利用、複製
- ソースコードの改良

# 禁止されていること
- このソースコードを悪意を持って利用する
- ニ次配布

# 補足
- ない

# 無駄情報
どうでもいいけどNickStarblastは私です (ClockClap)


# 取得
[![Download Latest](https://img.shields.io/badge/Download-v4.2.0-green.svg)](https://github.com/nanami-network/7mi-TCT/releases/download/v4.2.0/7mi-TCT-API-2.2-MC1.12.2.jar)

プラグインのバージョンは必ず1.12.2にしてください

まずは7mi-TCTをダウンロードして、どこかに保存しておきましょう。

# Maven <br>
**pom.xmlにこれを追加**

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.nanami-network.7mi-TCT</groupId>
        <artifactId>7mi-TCT-API</artifactId>
        <version>VERSION</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

# Gradle <br>
**build.gradleにこれを追加**

```
repositories {
    maven {
        url='https://jitpack.io'
    }
}
dependencies {
    compileOnly 'com.github.nanami-network.7mi-TCT:7mi-TCT-API:VERSION'
}
```
