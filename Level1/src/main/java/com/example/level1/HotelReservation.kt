package com.example.level1

/*
 * 호텔 예약 프로그램
 * 1. 사용자가 호텔 예약을 할 수 있는 메뉴를 표시하세요. (번호는 1~6번까지 만들어봐요.)
 * 2. 메뉴에서 4번을 누르면 호텔 예약 프로그램을 종료할 수 있어요
 * 3. 예약 플로우는 성함을 입력받고 방 번호를 입력받고 체크인 날짜를 입력받고 체크아웃 날짜를 입력받아요
 * 4. 1번을 눌러 방 예약을 받을 수 있도록 구현해 봐요
 * 5. 방 번호는 100~999호실까지 존재해요
 * 6. 체크인 날짜는 지금 날짜와 비교해서 이전날짜는 입력받을 수 없고 체크아웃 날짜는 체크인 날짜보다 이전이거나 같을 수는 없어요
 * 7. 입력이 완료되면 임의의 금액을 지급해 주고 랜덤으로 호텔 예약비로 빠져나가도록 구현해 봐요
 */
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.random.Random

class HotelReservation {
    private val rooms = mutableSetOf<Int>()
    private val dateFormat = SimpleDateFormat("yyyyMMdd")

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
        rooms.add(roomNumber)
        println("호텔 예약이 완료되었습니다.")
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
            4 -> {
                println("프로그램을 종료합니다.")
                break
            }
            else -> println("만드는 중")
        }
    }
}
