#curl -X POST https://www.strava.com/api/v3/oauth/token \
#  -d client_id=77478 \
#  -d client_secret=57a617064bbe699e8bafba9f9f9125682decba08 \
#  -d code=da38adaf03731d4f5920e87500333a8d8f049bd4 \
#  -d grant_type=authorization_code

#curl -X GET https://www.strava.com/oauth/authorize?client_id=77478?redirect_uri=localhost:8081

curl -X GET \
https://www.strava.com/api/v3/athlete \
-H 'Authorization: Bearer da38adaf03731d4f5920e87500333a8d8f049bd4'