#!/bin/bash

# if the docker system deamon is not running, if yes, start it up
sudo systemctl status docker || systemctl start docker

# var for word count of container named jrvs-psql
container_count=`docker container ls -a -f name=jrvs-psql | wc -l`
# input argument vars
cmd=$1
db_username=$2
db_password=$3

# case checking on (create, start, stop) arguments
case $cmd in
  # create a new container if the container does not exist already
  'create')

   # parameters are not equal to 3
    if [ "$#" -ne 3 ]; then
      echo "psql_docker: db_username or db_password were not entered"
      exit 1
    fi


    # container exists
    if [ $container_count -eq 2 ]; then
      echo "psql_docker: Docker container 'jrvs-psql is already created"
      exit 1
    fi



    # create the volume to hold the data and create and run the container with the entered db username and password
    docker run --name jrvs-psql -e POSTGRES_PASSWORD=${db_password} -e POSTGRES_USER=${db_username} -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
    #$? - 0 if true/1 false
    exit $?;;

  # Case When Start is the argument
  'start')
    if [ $container_count -ne 2 ]; then
      echo "psql_docker: docker container 'jrvs-psql' does not exist."
      exit 1
    fi

    # start the container
    docker container start jrvs-psql
    exit $?;;

  # Case When Stop is the argument
  'stop')
    if [ $container_count -ne 2 ]; then
      echo "psql_docker: docker container 'jrvs-psql' does not exist."
      exit 1
    fi

    # stop the container
    docker container stop jrvs-psql
    exit $?;;

  *)
    echo "psql_docker: Entered invalid command line argument."
    exit 1;;
esac