A basic custom JMeter sampler for reading from Azure Blob Storage.

The code is heavily inspired by [this quickstart repo](https://github.com/Azure-Samples/storage-blobs-java-quickstart)

Built against Apache JMeter 5.1.1, and will break horribly if used on other versions (but changing the version in pom.xml should work)

## Usage

1. `mvn clean package` to build a zip file containing all the JARs
2. in your Apache JMeter directory run `unzip blobstorage-1.0-SNAPSHOT.zip` and allow it to override files
3. create a new Java sampler, and the classname `protocol.java.org.apache.jmeter.protocol.java.sampler.BlobStorage` should be available to use
4. give it a container name, credentials, and a file name to pull

## Non-features

* No deserialization is attempted, it is assumed the file is plain text
* No support for listing or uploading of files yet
* Not built to work with versions of JMeter other than 5.1.1

PRs appreciated for the above