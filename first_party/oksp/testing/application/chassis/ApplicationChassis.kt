interface ApplicationChassis : AutoClosable {
  suspend fun run(application: Application): KspRunner.Result

  suspend fun run(applicationClass: Class<out Application>): KspRunner.Result
}
