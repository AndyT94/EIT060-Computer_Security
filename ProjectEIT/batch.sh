echo "Enter name "
read name

truststore = "${name}truststore"
keystore = "${name}keystore"
csr = "${name}.csr"
cer = "${name}.cer"
caCert = "certificate/ca-certificate.pem"
caKey = "certificate/ca-key.pem"

#import CA certifcate to truststore
keytool -import -file certificate/ca-certificate.pem -alias CA -keystore $truststore

#generate keypair
keytool -keystore $keystore -genkeypair -alias $name

#generate csr
keytool -keystore $keystore -certreq -alias $name -keyalg rsa -file $csr

#sign
openssl x509 -req -CA $caCert -CAkey $caKey -in $csr -out $cer -days 365 -CAcreateserial

#import CA certificate to keystore
keytool -import -keystore $keystore -file $caCert -alias CA

#import signed certificate
keytool -import -keystore $keystore -file $cer -alias $name 
