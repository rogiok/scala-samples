package crypto

import org.junit.Test
import java.security._
import java.io._
import javax.crypto.Cipher

/**
 * Created by Rogerio on 4/27/14 3:16 PM.
 */
class RsaTest {

  @Test def generateKeysTest() {

    val keyGen = KeyPairGenerator.getInstance("RSA")

    keyGen.initialize(1024)

    val key = keyGen.generateKeyPair()

    saveKey(key.getPublic, s"${System.getProperty("user.dir")}/public.key")
    saveKey(key.getPrivate, s"${System.getProperty("user.dir")}/private.key")

  }

  def saveKey(key: Key, filename: String) = {
    val output = new ObjectOutputStream(new FileOutputStream(new File(filename)))

    output.writeObject(key)

    output.close()
  }

  @Test def encryptTest() {

    var cipherText: Array[Byte] = null

    val publicKeyFile = new ObjectInputStream(new FileInputStream(new File(s"${System.getProperty("user.dir")}/public.key")))
    val privateKeyFile = new ObjectInputStream(new FileInputStream(new File(s"${System.getProperty("user.dir")}/private.key")))

    val publicKey = publicKeyFile.readObject().asInstanceOf[PublicKey]
    val privateKey = privateKeyFile.readObject().asInstanceOf[PrivateKey]

    try {
      // get an RSA cipher object and print the provider
      val encryptCipher = Cipher.getInstance("RSA")

      // encrypt the plain text using the public key
      encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey)

      cipherText = encryptCipher.doFinal("Hello World!!!".getBytes)


      val dencryptCipher = Cipher.getInstance("RSA")

      dencryptCipher.init(Cipher.DECRYPT_MODE, privateKey)

      val finalText = dencryptCipher.doFinal(cipherText)

      println(new String(finalText))

      assert("Hello World!!!" == new String(finalText))

    } catch {
      case e: Exception => e.printStackTrace()
    }


  }

}
