
## API Reference


#### API to get AWS EC2 and S3 services

```http
  POST /services
  
  output 0e9e2ad6-cd91-46e7-a112-7e044a4ebe9c
```
| Parameter      | Return  | Description       |
|:---------------|:--------|:------------------|
| `List<String>` | `jobId` | This method  asynchronously discover EC2 instances in the Mumbai Region in one thread and S3 buckets in another thread and persist the result in DB |``


will persist this type of data in db

```json
{
  "_id" : ObjectId("6674e6127a9f4d485d47e15f"),
  "jobId" : "a09d71fd-7a65-4319-95c0-229b6c1e8b50",
  "bucketEntity": [
    {
      "_id" : ObjectId("6674e6147a9f4d485d47e160"),
      "jobId" : "a09d71fd-7a65-4319-95c0-229b6c1e8b50",
      "name" : "nimesaassignmentbucket1",
      "creationDate" : ISODate("2023-11-16T14:13:27.000+0000")
    },
    {
      "_id" : ObjectId("6674e6147a9f4d485d47e161"),
      "jobId" : "a09d71fd-7a65-4319-95c0-229b6c1e8b50",
      "name" : "nimesaassignmentbucket2",
      "creationDate" : ISODate("2023-11-16T14:13:39.000+0000")
    }],
  "instanceEntity" : [
    {
      "_id" : ObjectId("6674e6147a9f4d485d47e164"),
      "jobId" : "a09d71fd-7a65-4319-95c0-229b6c1e8b50",
      "imageId" : "ami-02a2af70a66af6dfb",
      "instanceId" : "i-017e6519856f51d35",
      "instanceType" : "t2.micro",
      "keyName" : "Nimesa-Delhi-Acc-Key",
      "privateDnsName" : "ip-172-31-1-30.ap-south-1.compute.internal",
      "privateIpAddress" : "172.31.1.30",
      "publicDnsName" : "",
      "stateTransitionReason" : "User initiated (2023-11-16 14:15:40 GMT)",
      "subnetId" : "subnet-0bd1e8415e99b1324",
      "vpcId" : "vpc-0d8fcd1dc2d6bde7f",
      "architecture" : "x86_64",
      "clientToken" : "6c022089-2505-407a-b8e6-1ca94c58d11d",
      "ebsOptimized" : false,
      "enaSupport" : true,
      "hypervisor" : "xen",
      "rootDeviceName" : "/dev/xvda",
      "rootDeviceType" : "ebs",
      "sourceDestCheck" : true,
      "virtualizationType" : "hvm",
      "bootMode" : "uefi-preferred",
      "platformDetails" : "Linux/UNIX",
      "currentInstanceBootMode" : "legacy-bios"
    }],
  "status" : "SUCCESS",
  "isDeleted" : false,
  "version" : NumberLong(1),
  "_class" : "com.surajlunthi.RestAWS.model.Job"
}
```

```http
  GET /jobStatus/{jobId}
  output "SUCCESS"
```
| Parameter | Return                                       | Description                                                          |
|:----------|:---------------------------------------------|:---------------------------------------------------------------------|
| `jobId`   | `Job Status { SUCCESS,IN_PROGRESS,FAILED } ` | This method return the job current status SUCCESS,IN_PROGRESS,FAILED |``


```http
   GET /discovery/{services}
```
| Parameter | Return                                       | Description                                                                           |
|:----------|:---------------------------------------------|:--------------------------------------------------------------------------------------|
| `services`   | `List<String> ` | This method  For S3 return List of S3 Buckets and For EC2 return List of Instance IDs |``


```http
  POST /bucket/{bucketName}
  output 294472b4-7a2c-47c6-be7d-18f42eaa5398
```
| Parameter    | Return                                      | Description  |
|:-------------|:--------------------------------------------|:-------------|
| `bucketName` | `JobId`                                          | This method Discover all the File Names in the S3 bucket and persist in the DB |``


will persist this type of data in db

```json
{
  "_id" : ObjectId("6674eaa73e60995fbf71ef37"),
  "jobId" : "fb4bb491-71cc-4d88-83ab-ab1b4f7974d4",
  "bucketData" : [
    {
      "key" : "New folder/Test-File.txt",
      "lastModified" : ISODate("2023-12-01T12:35:31.000+0000"),
      "eTag" : "d41d8cd98f00b204e9800998ecf8427e",
      "size" : NumberLong(0),
      "storageClass" : "STANDARD"
    }
  ],
  "bucket" : "nimesaassignmentbucket1",
  "status" : "SUCCESS",
  "version" : NumberLong(0),
  "_class" : "com.surajlunthi.RestAWS.model.ObjectEntity"
}
```

```http
   GET /discovery/{services}
```
| Parameter | Return                                       | Description                                                                           |
|:----------|:---------------------------------------------|:--------------------------------------------------------------------------------------|
| `services`   | `List<String> ` | This method  For S3 return List of S3 Buckets and For EC2 return List of Instance IDs |``



```http
  GET /bucketCount/{bucket}
  output 6
```
| Parameter     | Return             | Description                                          |
|:--------------|:-------------------|:-----------------------------------------------------|
| `bucketName ` | `count of objects` | This method will return the count of objects from db |``


```http
  GET /bucketMatching/{bucketName}
```
| Parameter                  | Return             | Description                                                        |
|:---------------------------|:-------------------|:-------------------------------------------------------------------|
| `bucketName  and pattern ` | `count of objects` | This method will return the list of file name matching the pattern |``

```json
{
  "New folder/Test-File - 2.txt",
  "New folder/Test-File - 3.txt",
  "New folder/Test-File - 4.txt",
  "New folder/Test-File.txt"
}
```
## Swagger Link
http://localhost:8080/swagger-ui/index.html#/

## Author

- [@surajlunthi](https://github.com/surajlunthi)