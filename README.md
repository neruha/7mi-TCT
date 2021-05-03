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

まずは7mi-TCTをダウンロードして、どこかに保存しておきましょう。
(今回は例として `~/nanamitct/` にjarファイルを置きます)

##Maven
**pom.xmlにこれを追加**

```xml
<dependencies>
    <dependency>
        <groupId>me.clockclap</groupId>
        <artifactId>nanami-tct-api</artifactId>
        <version>3.3.0</version>
        <scope>system</scope>
        <systemPath>~/nanamitct/nanami-tct-api-3.3.0.jar</systemPath>
    </dependency>
</dependencies>
```

##Gradle
**build.gradleにこれを追加**

```
dependencies {
    compile files("~/nanamitct/nanami-tct-api-3.3.0.jar")
}
```
