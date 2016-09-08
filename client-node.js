//http://mitsuruog.github.io/what-mqtt/
var mqtt    = require('mqtt');
var readline = require('readline');
var client  = mqtt.connect('mqtt://test.mosquitto.org');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

client.on('connect', function () {
  client.subscribe('sensor/temperatura');
});

client.on('message', function (topic, message) {
  console.log(message.toString());
});

var iv = setInterval( function() {
  var randInt = Math.floor(Math.random()*100);
  client.publish('sensor/temperatura', ''+randInt);
  console.log(randInt);
}, 2500 );


//rl.on('line', (input) => {
//  client.publish('sensor/temperatura', input);
//  console.log(`Enviando: ${input}`);
//});
