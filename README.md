# Cloud Native WorkShop

https://github.com/Pivotal-Japan/cloud-native-workshop/blob/master/spring-boot.md
--


http://localhost:8888/{name}/{env}/{label}にアクセスすることで、アプリケーション毎の環境(profile)毎のコンフィギュレーションを取得できる。

name=アプリケーション名
env=profile名 (デフォルトはdefault)
label=branch名 (デフォルトはmaster)


--
membership.properies
```
eureka.client.serviceUrl.defaultZone=http://localhost:8762/eureka
```

eureka-server.properties
```
server.port=8762
```

--

## multiple eureka

metflix-configはmultiple-eurekaを使用する

eureka-server1.properties
```
server.port=${PORT:8762}
spring.profiles=original
eureka.instance.hostname=peer1
eureka.client.serviceUrl.defaultZone=http://peer2:8763/eureka/
```

eureka-server2.properties
```
server.port=${PORT:8763}
spring.profiles=replica
eureka.instance.hostname=peer2
eureka.client.serviceUrl.defaultZone=http://peer1:8762/eureka/
```

/etc/hosts
```
127.0.0.1 peer1 peer2
```

membership.properties
```
eureka.client.serviceUrl.defaultZone=http://peer1:8762/eureka,http://peer2:8763/eureka
```

```
java -jar build/libs/eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=original
java -jar build/libs/eureka-server-0.0.1-SNAPSHOT.jar --spring.profiles.active=replica
```
http://www.slideshare.net/makingx/spring-cloud-netflix

> インメモリのキャッシュにメタデータを保持し ているので、自動復旧機能があるプラットフ ォーム(Cloud Foundryなど)上であれば Stand Aloneモードで十分