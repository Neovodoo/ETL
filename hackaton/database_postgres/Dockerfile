FROM postgres:14.2-alpine

# RUN apk update
# RUN apk add --no-cache python3 py3-pip postgresql-plpython3

# COPY ./cfg/requirements.txt /var/lib/postgresql/cfg/requirements.txt
# RUN python3 -m pip install -r /var/lib/postgresql/cfg/requirements.txt

COPY ./init/ /docker-entrypoint-initdb.d/