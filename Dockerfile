# 1. Берем за основу готовый образ Linux с Java 8 и Tomcat 9
FROM tomcat:9.0-jdk8-openjdk

# 2. (Опционально) Удаляем стандартное приложение Tomcat "ROOT", чтобы наше стало главным
RUN rm -rf /usr/local/tomcat/webapps/ROOT

# 3. Копируем наш WAR файл внутрь контейнера
# Мы переименовываем его в ROOT.war, чтобы приложение открывалось по адресу localhost:8080/ (без префикса)
COPY backend-app/target/employee-app.war /usr/local/tomcat/webapps/ROOT.war

# 4. Говорим Докеру, что контейнер будет слушать порт 8080
EXPOSE 8080

# 5. Команда запуска Tomcat (она уже есть в базовом образе, но для ясности оставим)
CMD ["catalina.sh", "run"]
