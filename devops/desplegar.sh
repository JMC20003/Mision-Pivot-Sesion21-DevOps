#!/usr/bin/env sh
set -eu

if [ "$#" -ne 2 ]; then
    echo "Uso: $0 <dev|cert> <jar>" >&2
    exit 2
fi

: "${DEPLOY_TOKEN:?DEPLOY_TOKEN debe estar configurado}"

case "$1" in
    dev|cert) ;;
    *)
        echo "Ambiente no soportado: $1" >&2
        exit 2
        ;;
esac

if [ ! -f "$2" ]; then
    echo "No se encontro el artefacto: $2" >&2
    exit 1
fi

echo "Despliegue simulado de CuotaFacil en $1"
