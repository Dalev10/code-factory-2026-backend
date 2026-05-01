#!/bin/bash

# Carpeta raíz del proyecto (ajústala si es necesario)
ROOT_DIR="$(cd "$(dirname "$0")/.." && pwd)"

# Archivo de salida
OUTPUT_FILE="$(dirname "$0")/backend-code.txt"

# Limpia el archivo si ya existe
> "$OUTPUT_FILE"

# Encuentra todos los archivos .java
find "$ROOT_DIR/src" -type f -name "*.java" | while read -r filepath; do
  echo "$filepath" >> "$OUTPUT_FILE"
  echo "" >> "$OUTPUT_FILE"
  cat "$filepath" >> "$OUTPUT_FILE"
  echo -e "\n\n" >> "$OUTPUT_FILE"
done

echo "Todos los scripts han sido escritos en $OUTPUT_FILE"