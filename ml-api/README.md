# ml-api
Machine Learning Model API using YOLOv5 with FAST API

### Getting start for this project

```
uvicorn main:app --reload --host 0.0.0.0 --port 8000
```

### Documentation

This project is have 3 endpoint for the API :
1. /notify/v1/health

    This endpoint is for checking the health of the API. The response will be like this :
    ```
    {
        "status": "OK"
    }
    ```

2. /object-to-json
3. /object-to-img

