# 7mi-TCT
ななみTCTのリポジトリ
ソースコードは自由に利用できますが、下記の許可されていること、禁止されていることについてはよく目を通してください。

# 許可されていること
- 個人利用
- ソースコードの利用、複製
- ソースコードの改良

# 禁止されていること
- このソースコードを悪意を持って利用する
- 配布

# 補足
- ない

# 無駄情報
どうでもいいけどNickStarblastは私です (ClockClap)


# 取得

[![](https://jitpack.io/v/nanami-network/7mi-TCT.svg)](https://jitpack.io/#nanami-network/7mi-TCT)

※まだできません。

##Maven
**pom.xmlにこれらを追加**
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

```xml
<dependencies>
    <dependency>
        <groupId>com.github.nanami-network</groupId>
        <artifactId>7mi-TCT</artifactId>
        <version>v3.3.0</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

##Gradle
**build.gradleにこれらを追加**
```
repositories {
    maven { url 'https://jitpack.io' }
}
```

```
dependencies {
    compile 'com.github.nanami-network:7mi-TCT:v3.3.0'
}
```
