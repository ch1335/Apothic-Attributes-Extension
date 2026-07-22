---
name: verify
summary: 验证 NeoForge 模组的构建、数据生成和运行加载
---

# Verify

1. 执行 `./gradlew.bat --no-daemon clean build`。
2. 执行 `./gradlew.bat --no-daemon runData`，确认属性 tag provider 完成且 `src/generated/resources/data/apothic_attributes_extension/tags/attribute/attribute_sort.json` 存在。
3. 执行 `./gradlew.bat --no-daemon runServer`，等日志出现 `DedicatedServer]: Done` 后终止进程；搜索 Mixin injection 错误。
4. 执行 `./gradlew.bat --no-daemon runClient`，等日志出现 LWJGL backend、OpenAL initialized 后终止进程；搜索 Mixin injection 错误。
5. SERVER config 位于 `run/config/apothic_attributes_extension-server.toml`。越界值会由 `ConfigTracker` 自动修正；验证后恢复默认值。
6. 运行配置日志较多，优先从后台输出或 `run/logs/latest.log` 搜索 `InvalidInjectionException|InjectionError|Mixin apply failed|Critical injection failure|FATAL|Done`。
