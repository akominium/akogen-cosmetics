# Akogen Cosmetics

Minecraft **1.21.11** için Fabric istemci modu. **Home** gardırobu açar.

1. Minecraft 1.21.11 ile uyumlu Fabric Loader ve Fabric API kur.
2. Akogen JAR dosyasını profilin `mods` klasörüne koy.
3. Minecraft'ı başlat. Çok oyunculu kozmetik görünümü için arkadaşlarınla aynı Akogen sürümünü kullan.

## Kaynaktan derleme

Windows PowerShell'de `./Build.ps1` çalıştır. Derleyici `JAVA_HOME` veya `PATH` üzerinden bulunur.
Modrinth kütüphane önbelleği farklı bir konumdaysa `-Libraries` ile belirt.
Derleme Fabric Loader 0.19.3, Sponge Mixin 0.17.3+mixin.0.8.7, Gson 2.13.2 ve ASM 9.10.1 kütüphanelerini bu önbellekten alır.
Çıktı `akogen-1.21.11-fabric.jar` olur. Oyunu çalıştırmak için Java 21 veya üzeri gerekir.

## Çok oyunculu paylaşım

Kozmetik senkronizasyonu varsayılan olarak `broker.emqx.io:8883` üzerindeki ortak MQTT aktarıcısına TLS ile bağlanır. Trafik, oyuncu UUID'sinden türetilen kanal kimliğini, takılı kozmetik numaralarını ve animasyon durumunu içerir. Aktarıcı herkese açıktır. Paylaşımı kapatmak için profilin `config/akogen/cosmetic-sync.json` dosyasındaki `enabled` değerini `false` yap.

Kişisel profil ayarları, loglar ve derleme çıktıları `.gitignore` kapsamındadır. GitHub Release'e derlenmiş JAR, repoya bu kaynak ağacı yüklenir.

## Lisans

[MIT lisansı](LICENSE), Akogen'in özgün kaynak kodu, derleme araçları ve belgeleri için geçerlidir. `resources/cosmetics.zip` ve üçüncü taraf varlıkları bu lisansın kapsamı dışındadır. Üçüncü taraf varlıklarının dağıtımı ilgili hak sahiplerinin izinlerine ve koşullarına tabidir.
