import com.google.crypto.tink.*
import com.google.crypto.tink.aead.AeadConfig


/**
 * Various encryption methods using Tink (https://developers.google.com/tink)
 * Encryption key can be generated with Tinkey: ./tinkey create-keyset --key-template AES128_GCM
 */
object Encryption {
    /**
     * Decrypt the given data with the provided key.
     */
    fun aeadDecrypt(data: ByteArray, key: String, associatedData: ByteArray? = null): String {
        AeadConfig.register()
        val keysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withString(key))
        val aead = keysetHandle.getPrimitive(Aead::class.java)
        return String(aead.decrypt(data, associatedData))
    }

    /**
     * Encrypt the given data with the provided key.
     */
    fun aeadEncrypt(data: String, key: String, associatedData: ByteArray? = null): ByteArray {
        AeadConfig.register()
        val keysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withString(key))
        val aead = keysetHandle.getPrimitive(Aead::class.java)
        return aead.encrypt(data.toByteArray(), associatedData)
    }

}