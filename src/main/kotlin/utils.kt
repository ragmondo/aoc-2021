class DataReader() { }

fun getData(dayNumber: Int, sample: Boolean = false) = DataReader::class.java.getResource("/day${dayNumber}." +  if(sample) "sample" else "dat").readText().split("\n")
