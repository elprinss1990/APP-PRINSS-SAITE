package com.example

import com.example.ui.components.FUTCardType
import com.example.ui.components.FUTStats
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun futCardType_fromCardType_mapping() {
    assertEquals(FUTCardType.TOTY, FUTCardType.fromCardType("TOTY"))
    assertEquals(FUTCardType.GOLD_RARE, FUTCardType.fromCardType("GOLD RARE"))
    assertEquals(FUTCardType.ICONS, FUTCardType.fromCardType("ICON PRIME"))
    assertEquals(FUTCardType.HEROES, FUTCardType.fromCardType("HERO SPECIAL"))
    assertEquals(FUTCardType.ULTIMATE_ELITE, FUTCardType.fromCardType("PRINSS VIP ELITE"))
  }

  @Test
  fun futStats_creation() {
    val stats = FUTStats(
      pace = 94,
      shooting = 91,
      passing = 88,
      dribbling = 95,
      defending = 45,
      physical = 82
    )
    assertEquals(94, stats.pace)
    assertEquals(91, stats.shooting)
    assertEquals(88, stats.passing)
    assertEquals(95, stats.dribbling)
    assertEquals(45, stats.defending)
    assertEquals(82, stats.physical)
  }
}

