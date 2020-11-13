import * as readline from 'readline';
import { Enigma } from './src/enigma';

let enigma: Enigma;

let rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
});

rl.on('line', (input) => {
    if (input.charAt(0) == '*') {
        const inputs = input.split(' ');
        enigma = new Enigma(inputs[1], inputs[2], inputs[3], inputs[4], inputs[5]);
        return;
    }

    if (enigma == null) {
        throw new Error("engima must be initialized with a setting string first.");
    }

    console.log(enigma.encrypt(input));
});
