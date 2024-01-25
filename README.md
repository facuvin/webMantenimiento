# IDEA GENERAL
La empresa BIOS Servi Tutti Incorporated, dedicada a brindar servicios de mantenimiento para el
hogar, le ha encomendado a su equipo el desarrollo de un sistema web para la gestión de sus actividades.
# DESCRIPCIÓN DEL PROBLEMA
Los usuarios del sistema serán los administrativos, los clientes y los técnicos. De todos ellos se debe
registrar su nombre de usuario, su clave de acceso, su nombre completo, y su dirección de correo
electrónico. Todos los tipos de usuario serán registrados por un administrativo, por lo que el sistema
ya debe contar al menos con un usuario administrativo precargado. También será posible modificar
los datos de los usuarios. Los clientes no podrán eliminarse una vez registrados, pero los
administrativos sí. Los técnicos podrán eliminarse, pero si tienen solicitudes de visita asignadas deberá
realizarse una baja lógica.
De los administrativos también se debe registrar su fecha de ingreso a la empresa.
De los clientes se debe registrar además de los datos básicos de usuario, su cédula, dirección, y
teléfono de contacto.
Los técnicos cuentan además con un teléfono de contacto y un conjunto de competencias (al menos
una). Estas competencias tienen un id numérico autogenerado y un nombre (por ejemplo, albañil,
carpintero, electricista, sanitario, pintor, gasista, etc.). También serán registradas por un usuario
administrativo, y podrán modificarse, y eliminarse si nunca fueron vinculadas a un técnico o a una
solicitud de visita.
Los clientes firman un contrato con la empresa, a partir de lo cual comienzan a pagar una cuota
mensual. Dicho contrato es registrado en el sistema por un administrativo y tiene un número, el
cliente al que corresponde, una fecha de firma y una imagen escaneada del mismo. Si el cliente decide
en algún momento cancelar su contrato, el administrativo registra una cancelación de contrato
vinculada al mismo, con número, fecha y motivo. Un cliente no puede tener dos contratos activos al
mismo tiempo, pero sí puede volver a contratar los servicios de la empresa luego de haber cancelado
un contrato previo.
El sistema debe permitir registrar el pago de las mensualidades de los clientes, ingresando el importe,
y una descripción (además de un número autogenerado y la fecha tomada automáticamente del
sistema). El importe es ingresado por el administrativo y puede variar ya que, si el cliente se atrasa en
sus pagos, el administrativo le cobrará el o las mensualidades atrasadas con cierto recargo, y sólo
registrará el pago si el cliente se pone al día.
Si el cliente se encuentra al día con las mensualidades (es decir con un pago registrado en los últimos
30 días), podrá hacer uso de hasta 10 horas/técnico por mes. Para esto el cliente, a través de la
aplicación web, solicitará una visita, indicando la descripción de su problema, el rango horario
(inicio y fin) en el que puede recibir al técnico, y una lista opcional de competencias requeridas. Además,
se registrará automáticamente el número autogenerado de la solicitud y su fecha y hora.
El administrativo podrá consultar las solicitudes de visita pendientes y asignarles un técnico que cuente
con las competencias adecuadas requeridas en la solicitud, o cualquier técnico a su criterio en caso de
que el cliente no haya indicado competencias requeridas (o que no haya ningún técnico con las
competencias indicadas).
Los técnicos podrán ver las solicitudes de visita pendientes que tienen asignadas, y luego de realizada
la misma podrán registrar su informe de visita (con número autogenerado), indicando la fecha y hora
de la misma, la descripción de las reparaciones efectuadas (los materiales corren a cuenta del
cliente), y la duración de su visita en cantidad de horas. Al finalizar el registro del informe de visita,
el sistema indicará si el cliente está dentro de su límite mensual de 10 horas/técnico y no se le cobrará,
pero si se excede, el sistema informará y registrará la cantidad de horas adicionales que el técnico debe
cobrarle al cliente.
