import {Reflector, Rotor} from './rotor';

const CONFIG_REG = /^[A-Z]{4}$/
const REFLECTOR_REG = /^[B|C]{1}$/

export class Enigma {

    reflector: Reflector;
    left: Rotor;
    mid: Rotor;
    right: Rotor;

    constructor(reflector: string, left: string, 
        mid: string, right: string, config: string) {
        if (!CONFIG_REG.test(config)) {
            throw new Error('Config must be 4 [A-Z]');
        }

        const h = {};
        this.reflector = new Reflector(config.charAt(0), reflector);
        this.left = new Rotor(config.charAt(1), left);
        h[left] = true; 

        if (h[mid]) {
            throw new Error('Unable to reuse same Rotor');
        } 

        this.mid = new Rotor(config.charAt(2), mid);
        h[mid] = true;

        if (h[right]) {
            throw new Error('Unable to reuse same Rotor');
        }
        this.right = new Rotor(config.charAt(3), right);
    }

    encrypt(msg: string): string {
        let encrypted = '';
        let temp = '' 
        for (let i = 0; i < msg.length; i++) {
            if (temp.length == 5) {
                encrypted += temp + ' ';
                temp = '';
            }

            const c = msg.charAt(i);
            if (/^[A-Za-z]{1}$/.test(c)) {
                this.rotate();
                temp += this.perm(c.toUpperCase());
            }
        }
        encrypted += temp;
        return encrypted.trimRight();
    }

    private rotate() {
        if (this.mid.isInNotch()) {
            this.mid.advance();
            this.left.advance();
        } else if (this.right.isInNotch()) {
            this.mid.advance();
        } 

        this.right.advance();
    }

    private perm(c: string): string {
        c = this.right.permutate(c);
        c = this.mid.permutate(c);
        c = this.left.permutate(c);
        c = this.reflector.permutate(c);
        c = this.left.reverse(c);
        c = this.mid.reverse(c);
        const out = this.right.reverse(c);
        return out;
    }
}
