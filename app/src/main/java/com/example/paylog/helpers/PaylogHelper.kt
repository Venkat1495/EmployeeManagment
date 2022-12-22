package com.example.paylog.helpers

class PaylogHelper {

    companion object {
        fun timeDifference(time1: String,time2: String) : String {
            var time1Arr = time1.split(':')
            var time2Arr = time2.split(":")
            println(time1Arr)
            println(time2Arr)
            var time1Hrs = time1Arr[0].toInt()
            var time1Min = time1Arr[1].toInt()
            var time2Hrs = time2Arr[0].toInt()
            var time2Min = time2Arr[1].toInt()

            if(time2Min > time1Min) {
                time1Hrs -= 1
                time1Min += 60
            }
            var resultMin = time1Min - time2Min
            var resultHrs = time1Hrs - time2Hrs
            print("$resultHrs:$resultMin")
            return "$resultHrs:$resultMin"
        }

        fun amountCalculator(totalTime: String, hourlyPay:Int) : Double {
            var timeArr = totalTime.split(":")
            var timeMins = timeArr[1].toInt()
            var timeHrs  = timeArr[0].toInt()
            var amount =  (timeHrs * hourlyPay) +  (timeMins * (hourlyPay.toFloat() / 60))
            println(amount)
            println(Math.round(amount * 10.0) / 10.0)
            return Math.round(amount * 10.0) / 10.0
        }
    }
}