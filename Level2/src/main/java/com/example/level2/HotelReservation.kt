package com.example.level2

/*
 * lv2
 * 1. 메뉴에서 2번을 눌러 호텔 예약자 목록을 보여줘요. (예시. 1. 사용자: ㅇㅇㅇㅇ, 방 번호: ㅇㅇㅇ호, 체크인: 2023-07-21. 체크아웃: 2023-08-01)
 * 2. 메뉴에서 3번을 눌러 호텔 예약자 목록을 정렬 기능을 사용하여 체크인 날짜순으로 오름차순으로 정렬해 봐요
 * 3. 예약 플로우를 수정해 봐요. 해당 체크인 체크아웃 날짜에 선택한 방 번호를 예약 가능한지 불가능한지 판단하게 변경해 봐요. 예약이 불가능하면 체크인, 체크아웃 날짜를 변경해서 다시 검사해 보는 플로우를 만들어봐요.
 */
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import kotlin.random.Random

data class Reservation(
    val name: String,
    val roomNumber: Int,
    val checkInDate: String,
    val checkOutDate: String
)

class HotelReservation {
    private val rooms = mutableSetOf<Int>()
    private val dateFormat = SimpleDateFormat("yyyyMMdd")
    private val reservationDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val reservationList = mutableListOf<Reservation>()

    fun showMenu() {
        println("[메뉴]")
        println("1. 방 예약 2. 예약 목록 출력 3. 예약 목록(정렬) 출력 4. 시스템 종료 5. 금액 입금-출금 내역 목록 출력 6. 예약 변경/취소")
    }

    fun reserveRoom() {
        println("예약자 분의 성함을 입력하세요")
        val name = readLine()

        println("예약할 방 번호를 입력해주세요")
        var roomNumber = readLine()?.toIntOrNull()
        // 방 번호는 100~999
        while (roomNumber == null || roomNumber !in 100..999 || roomNumber in rooms) {
            println("올바르지 않은 방번호 입니다. 방번호는 100~999 영역 이내입니다.")
            roomNumber = readLine()?.toIntOrNull()
        }

        println("체크인 날짜를 입력하세요 표기형식. 20230631")
        var checkInDate = readLine()
        while (!isBeforeValidDate(checkInDate, Date())) {
            println("체크인은 지난날은 선택할 수 없습니다.")
            checkInDate = readLine()
        }

        println("체크아웃 날짜를 입력하세요 표기형식. 20230631")
        var checkOutDate = readLine()
        while (!isAfterValidDate(checkOutDate, checkInDate)) {
            println("체크인 날짜보다 이전이거나 같을 수는 없습니다.")
            checkOutDate = readLine()
        }

        val reservationAmount = Random.nextInt(100, 1000)
        println("예약이 완료되었습니다. 예약비로 $reservationAmount 원이 빠져 나갑니다.")

        val reservation = Reservation(name!!, roomNumber, checkInDate!!, checkOutDate!!)
        reservationList.add(reservation)
        rooms.add(roomNumber)
        println("호텔 예약이 완료되었습니다.")
    }

    fun showReservationList() {
        if (reservationList.isEmpty()) {
            println("예약자가 없습니다.")
            return
        } else {
            println("호텔 예약자 목록입니다.")
            for ((index, reservation) in reservationList.withIndex()) {
                println("${index + 1}. 사용자: ${reservation.name}, 방 번호: ${reservation.roomNumber}, " +
                        "체크인: ${reservationDateFormat.format(dateFormat.parse(reservation.checkInDate))}, " +
                        "체크아웃: ${reservationDateFormat.format(dateFormat.parse(reservation.checkOutDate))}")
            }
        }
    }

    // 호텔 예약자 목록을 체크인 날짜순으로 오름차순으로 정렬하는 메소드
    fun showReservationListByCheckInDate() {
        if (reservationList.isEmpty()) {
            println("예약자가 없습니다.")
            return
        } else {
            println("호텔 예약자 목록입니다.")
            for ((index, reservation) in reservationList.sortedBy { it.checkInDate }.withIndex()) {
                println("${index + 1}. 사용자: ${reservation.name}, 방 번호: ${reservation.roomNumber}, " +
                        "체크인: ${reservationDateFormat.format(dateFormat.parse(reservation.checkInDate))}, " +
                        "체크아웃: ${reservationDateFormat.format(dateFormat.parse(reservation.checkOutDate))}")
            }
        }
    }

    // 체크인 날짜는 지금 날짜와 비교해서 이전 날짜는 입력 불가
    private fun isBeforeValidDate(date: String?, compareDate: Date?): Boolean {
        return try {
            val inputDate = dateFormat.parse(date)
            inputDate.after(compareDate)
        } catch (e: Exception) {
            false
        }
    }

    // 체크아웃 날짜는 체크인 날짜보다 이전이거나 같을 수는 없어요
    private fun isAfterValidDate(date: String?, compareDate: String?): Boolean {
        return try {
            val inputDate = dateFormat.parse(date)
            val compareDate = dateFormat.parse(compareDate)
            inputDate.after(compareDate)
        } catch (e: Exception) {
            false
        }
    }
}

fun main() {
    val hotelReservation = HotelReservation()

    while (true) {
        hotelReservation.showMenu()
        when (readLine()?.toIntOrNull()) {
            1 -> hotelReservation.reserveRoom()
            2 -> hotelReservation.showReservationList()
            3 -> hotelReservation.showReservationListByCheckInDate()
            4 -> {
                println("프로그램을 종료합니다.")
                break
            }
            else -> println("만드는 중")
        }
    }
}
