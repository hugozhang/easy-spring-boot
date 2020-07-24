# easy-spring-boot
## maven 模板 https://juejin.im/post/5bddc275e51d4505494095de

### 创建自定义模板

1.在maven项目下，执行mvn archetype:create-from-project，在target/generated-sources/archetype目录下生成Archetype project

2.cd target/generated-sources/archetype后，mvn install安装archetype project到本地仓库

### 使用自定义模板

1.在当前的目录下，mvn archetype:generate -DarchetypeCatalog=local，查看本地archetype列表

2.choose number，按步骤输入基本参数groupId/artifactId/version/package

3.在当前目录下，以artifactId为目录创建一个新的项目