# Database create

```shell
docker run -e POSTGRES_USER:postgres -e POSTGRES_PASSWORD:admin123 -d -p 5432:5432 postgres
```

Change ip of your docker-machine in `humberd.BeansConfigs.dataSource()`