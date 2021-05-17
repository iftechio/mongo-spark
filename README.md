# Jike-mongo-spark

> Fork from: [MongoDB Spark Connector](https://github.com/mongodb/mongo-spark)
> 
> Version: 2.4.x

## Publish

```shell
$ sbt 'set test in assembly := {}' publish
```

## Note

该项目中为 `MongoSpark.save()` 方法添加了RateLimit功能，是基于 `Guava.RateLimiter` 进行实现。就引入了一个 Guava 版本冲突的问题，
由于该项目中使用的还是 Spark 2.4.x，本来是可以使用 `org.spark_project.guava` 这个shard package，但是由于集群升级到了 Spark 3.0.x，
已经移除了这个package，改用 `com.google.guava` 正式的低版本包，没有包含 `Guava.RateLimiter` 类，导致因为找不到这个类报错。所以我们使用
assembly plugin的shard功能，将 `com.google.common` 修改为 `iftech`，并且由于assembly plugin打出来的jar名字是 
`jike-mongo-spark-1.0.4-SNAPSHOT-assembly.jar` 不会被其他的项目正常下载，所以更名为 `s"${name.value}_${scalaBinaryVersion.value}-${version.value}.jar"`，
这样做的副作用是会覆盖原始jar。