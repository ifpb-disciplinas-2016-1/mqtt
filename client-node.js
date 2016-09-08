var mqtt    = require('mqtt');
var readline = require('readline');
var client  = mqtt.connect('mqtt://test.mosquitto.org');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

client.on('connect', function () {
  client.subscribe('mitsuruog');
});

client.on('message', function (topic, message) {
  console.log(message.toString());
});

rl.on('line', (input) => {
  client.publish('mitsuruog', input);
  console.log(`Enviando: ${input}`);
});
