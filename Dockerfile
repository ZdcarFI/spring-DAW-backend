
# Usar imagen base con JDK 17
FROM openjdk:17-jdk-slim

# Instalar Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Crear directorio de trabajo
WORKDIR /app

# Copiar pom.xml primero (para cache de dependencias)
COPY pom.xml .

# Descargar dependencias
RUN mvn dependency:go-offline -B

# Copiar código fuente
COPY src ./src

# Construir la aplicación con información de debug
RUN mvn clean package -DskipTests -X

# Debug: Ver qué archivos se generaron
RUN echo "=== CONTENIDO DE TARGET ===" && \
    ls -la target/ && \
    echo "=== ARCHIVOS JAR ENCONTRADOS ===" && \
    find target/ -name "*.jar" -type f

# Encontrar y copiar el JAR principal
RUN JAR_FILE=$(find target/ -name "*.jar" -not -name "*-sources.jar" -not -name "*-javadoc.jar" | head -1) && \
    echo "JAR encontrado: $JAR_FILE" && \
    cp $JAR_FILE app.jar && \
    echo "JAR copiado como app.jar" && \
    ls -la app.jar

# Exponer puerto
EXPOSE 8080

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "app.jar"]