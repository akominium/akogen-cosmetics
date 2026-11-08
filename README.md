# akogen Cosmetics

Minecraft **1.21.11** için Fabric istemci modu.

1. Minecraft 1.21.11 ile uyumlu Fabric Loader ve Fabric API kur
2. akogen JAR dosyasını profilin mods klasörüne koy
3. Home Tuşu ile GUI'ı aç

## Çok oyunculu paylaşım

Kozmetik senkronizasyonu varsayılan olarak `broker.emqx.io:8883` üzerindeki ortak MQTT aktarıcısına TLS ile bağlanır. Trafik, oyuncu UUID'sinden türetilen kanal kimliğini, takılı kozmetik numaralarını ve animasyon durumunu içerir. Aktarıcı herkese açıktır. Paylaşımı kapatmak için profilin `config/akogen/cosmetic-sync.json` dosyasındaki `enabled` değerini `false` yap.

Kişisel profil ayarları, loglar ve derleme çıktıları `.gitignore` kapsamındadır. GitHub Release'e derlenmiş JAR, repoya bu kaynak ağacı yüklenir.

## Lisans

[MIT lisansı](LICENSE), akogen'in özgün kaynak kodu, derleme araçları ve belgeleri için geçerlidir. `resources/cosmetics.zip` ve üçüncü taraf varlıkları bu lisansın kapsamı dışındadır. Üçüncü taraf varlıklarının dağıtımı ilgili hak sahiplerinin izinlerine ve koşullarına tabidir.
