package com.android.szparag.flighter.utils

import android.Manifest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.math.absoluteValue

class PermissionsStringToRequestCodeTests {

  @Test
  fun `all permission strings (singles) should parse to requestCode properly`() {
    getAllSdkPermissionStrings(Manifest.permission::class.java).forEach { permissionString ->
      val requestCode = parseStringToRequestCode(permissionString)
      assertTrue(
          "Validation failed for permission string: $permissionString (generated requestCode: $requestCode)",
          validateRequestCode(requestCode)
      )
    }
  }

  @Test
  fun `all permission strings (groups) should parse to requestCode properly`() {
    getAllSdkPermissionStrings(Manifest.permission_group::class.java).forEach { permissionString ->
      val requestCode = parseStringToRequestCode(permissionString)
      assertTrue(
          "Validation failed for permission string: $permissionString (generated requestCode: $requestCode)",
          validateRequestCode(requestCode)
      )
    }
  }

  @Test
  fun `all permission strings (singles) should have unique requestCodes`() {
    val requestCodes = getAllSdkPermissionStrings(Manifest.permission::class.java).map { permissionString ->
      parseStringToRequestCode(permissionString)
    }

    assertEquals(requestCodes.size, requestCodes.distinct().size)
  }

  @Test
  fun `all permission strings (groups) should have unique requestCodes`() {
    val requestCodes = getAllSdkPermissionStrings(Manifest.permission_group::class.java).map { permissionString ->
      parseStringToRequestCode(permissionString)
    }

    assertEquals(requestCodes.size, requestCodes.distinct().size)
  }


  @Test
  fun `multiple permission string parsing should give the same value`() {
    val permissionRequestCode = parseStringToRequestCode(Manifest.permission.ACCESS_FINE_LOCATION)
    (0..10).forEach {
      val newRequestCode = parseStringToRequestCode(Manifest.permission.ACCESS_FINE_LOCATION)
      if (newRequestCode != permissionRequestCode)
        throw RuntimeException("RequestCodes differ, original: $permissionRequestCode, current: $newRequestCode")
    }
  }

  private fun getAllSdkPermissionStrings(permissionContainerClass: Class<*>): List<String> {
    return permissionContainerClass.fields.map { permissionContainerClass.getDeclaredField(it.name).get(null) as String }
  }

  private fun parseStringToRequestCode(input: String): Int =
      input.hashCode().absoluteValue

  private fun validateRequestCode(requestCode: Int) =
      requestCode > 0 && requestCode and -0x10000 != 0

}