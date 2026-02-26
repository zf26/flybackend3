# fly-search 搜索服务模块

## 📋 模块概述

基于 Elasticsearch 的搜索服务，提供医生、疾病、症状等多维度搜索功能。

**端口**: 10024  
**服务名**: fly-search

---

## 🎯 核心功能

### 1. 医生搜索
- 关键词搜索（姓名、擅长领域、简介）
- 多维度筛选（科室、医院、职称、评分、价格）
- 智能排序（评分、价格、问诊次数）
- 分页查询

### 2. 数据同步
- 从 MySQL 同步医生数据到 ES
- 支持全量同步和增量同步
- 索引管理（创建、删除、更新）

---

## 📡 API 接口

### 搜索医生（POST）
```http
POST /search/doctor
Content-Type: application/json

{
  "keyword": "呼吸内科",
  "department": "呼吸内科",
  "hospital": "上海市第一人民医院",
  "title": "主任医师",
  "minRating": 4.5,
  "maxPrice": 200,
  "status": 0,
  "sortBy": "rating",
  "sortOrder": "desc",
  "pageNum": 1,
  "pageSize": 20
}
```

**响应示例**:
```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "items": [
      {
        "id": 1,
        "name": "王心研",
        "title": "主治医师",
        "department": "呼吸内科",
        "hospital": "上海市第一人民医院",
        "specialties": "慢性咳嗽、哮喘、肺炎",
        "introduction": "从事呼吸内科临床工作10年...",
        "avatar": "http://...",
        "rating": 4.8,
        "consultationCount": 1520,
        "price": 150.0,
        "status": 0,
        "tags": "专业,耐心,响应快"
      }
    ],
    "total": 156,
    "pageNum": 1,
    "pageSize": 20,
    "totalPages": 8,
    "took": 45
  }
}
```

### 快速搜索（GET）
```http
GET /search/doctor?keyword=呼吸内科&department=呼吸内科&pageNum=1&pageSize=20
```

### 同步数据
```http
POST /search/doctor/sync?doctorId=1
```

### 删除索引
```http
DELETE /search/doctor/1
```

---

## 🔧 技术栈

- **Spring Boot 3.x**
- **Spring Data Elasticsearch**
- **Elasticsearch 8.x**
- **IK 分词器**（中文分词）

---

## 📊 ES 索引结构

### doctors 索引
```json
{
  "mappings": {
    "properties": {
      "id": { "type": "long" },
      "name": { 
        "type": "text",
        "analyzer": "ik_max_word",
        "search_analyzer": "ik_smart"
      },
      "title": { "type": "keyword" },
      "department": { "type": "keyword" },
      "hospital": { 
        "type": "text",
        "analyzer": "ik_max_word"
      },
      "specialties": { 
        "type": "text",
        "analyzer": "ik_max_word"
      },
      "introduction": { 
        "type": "text",
        "analyzer": "ik_max_word"
      },
      "avatar": { "type": "keyword" },
      "rating": { "type": "double" },
      "consultationCount": { "type": "integer" },
      "price": { "type": "double" },
      "status": { "type": "integer" },
      "tags": { "type": "keyword" }
    }
  }
}
```

---

## 🚀 部署步骤

### 1. 安装 Elasticsearch
```bash
# Docker 方式
docker run -d \
  --name elasticsearch \
  -p 9200:9200 \
  -p 9300:9300 \
  -e "discovery.type=single-node" \
  -e "ES_JAVA_OPTS=-Xms512m -Xmx512m" \
  elasticsearch:8.11.0
```

### 2. 安装 IK 分词器
```bash
# 进入容器
docker exec -it elasticsearch bash

# 安装 IK 分词器
./bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v8.11.0/elasticsearch-analysis-ik-8.11.0.zip

# 重启容器
docker restart elasticsearch
```

### 3. 配置应用
修改 `application.yml`:
```yaml
spring:
  elasticsearch:
    uris: http://localhost:9200
    username: elastic
    password: your_password
```

### 4. 启动服务
```bash
mvn spring-boot:run
```

### 5. 同步数据
```bash
curl -X POST http://localhost:10024/search/doctor/sync
```

---

## 🔍 搜索示例

### 1. 按关键词搜索
```bash
curl -X GET "http://localhost:10024/search/doctor?keyword=呼吸内科"
```

### 2. 按科室筛选
```bash
curl -X GET "http://localhost:10024/search/doctor?department=呼吸内科"
```

### 3. 综合搜索
```bash
curl -X POST http://localhost:10024/search/doctor \
  -H "Content-Type: application/json" \
  -d '{
    "keyword": "咳嗽",
    "department": "呼吸内科",
    "minRating": 4.5,
    "sortBy": "rating",
    "sortOrder": "desc",
    "pageNum": 1,
    "pageSize": 10
  }'
```

---

## 📈 扩展功能

### 1. 疾病搜索
创建 `DiseaseDocument` 和 `DiseaseSearchController`

### 2. 症状搜索
创建 `SymptomDocument` 和 `SymptomSearchController`

### 3. 知识库搜索
创建 `KnowledgeDocument` 和 `KnowledgeSearchController`

### 4. 搜索建议
实现自动补全和搜索建议功能

### 5. 搜索统计
记录搜索热词和用户搜索行为

---

## 🎯 优化建议

1. **缓存热门搜索**: 使用 Redis 缓存高频搜索结果
2. **搜索日志**: 记录搜索关键词，分析用户需求
3. **同义词扩展**: 配置医学同义词词典
4. **拼音搜索**: 支持拼音首字母搜索
5. **相关性调优**: 调整字段权重和评分算法

---

## 🐛 常见问题

### Q: Elasticsearch 连接失败
**A**: 检查 ES 服务是否启动，端口是否正确

### Q: 中文分词不生效
**A**: 确认 IK 分词器已安装并重启 ES

### Q: 搜索结果不准确
**A**: 调整字段权重，优化查询条件

---

## 📝 TODO

- [ ] 实现数据同步定时任务
- [ ] 添加搜索建议接口
- [ ] 实现疾病和症状搜索
- [ ] 添加搜索统计功能
- [ ] 优化搜索性能

