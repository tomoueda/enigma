const ROTOR_TYPES: string[] = ['I', 'II', 'III', 'IV', 'V', 'VI', 'VII', 'VIII'];
const REFLECTOR_TYPES: string[] = ['B', 'C'];
const ALPHA_CAP: RegExp = /^[A-Z]{1}$/;
const Z_VAL = 26;
const ASCII_A = 65;
const PERMS = new Map([
    ['I', ['AELTPHQXRU', 'BKNW', 'CMOY', 'DFG', 'IV', 'JZ', 'S']],
    ['II', ['FIXVYOMW', 'CDKLHUP', 'ESZ', 'BJ', 'GR', 'NT', 'A', 'Q']],
    ['III', ['ABDHPEJT', 'CFLVMZOYQIRWUKXSG', 'N']],
    ['IV', ['AEPLIYWCOXMRFZBSTGJQNH', 'DV', 'KU']],
    ['V', ['AVOLDRWFIUQ', 'BZKSMNHYC', 'EGTJPX']],
    ['VI', ['AJQDVLEOZWIYTS', 'CGMNHFUX', 'BPRK']],
    ['VII', ['ANOUPFRIMBZTLWKSVEGCJYDHXQ']],
    ['VIII', ['AFLSETWUNDHOZVICQ', 'BKJ', 'GXY', 'MPR']],
    ['B', ['AY', 'BR', 'CU', 'DH', 'EQ', 'FS', 'GL', 'IP', 'JX', 'KN', 'MO', 'TZ', 'VW']],
    ['C', ['AF', 'BV', 'CP', 'DJ', 'EI', 'GO', 'HY', 'KR', 'LZ', 'MX', 'NW', 'TQ', 'SU']],
]);

const NOTCH = new Map([
    ['I', ['Q']],
    ['II', ['E']],
    ['III', ['V']],
    ['IV', ['J']],
    ['V', ['Z']],
    ['VI', ['Z', 'M']],
    ['VII', ['Z', 'M']],
    ['VIII', ['Z', 'M']],
]);

export class BaseRotor {

    offset: number;
    rotarType: string;

    advance(): void {
        this.offset = (this.offset + 1) % Z_VAL;
    }

    permutate(c: string): string {
        if (!ALPHA_CAP.test(c)) {
            throw new Error('Can only permutate one character');
        }
        const obj = this.findString(c);
        const s = obj.s;
        let idx = (obj.idx + 1) % s.length;
        return this.offsetBack(idx, s);
    }

    reverse(c: string): string {
        if (!ALPHA_CAP.test(c)) {
            throw new Error('Can only permutate one character');
        }
        const obj  = this.findString(c);
        let idx = obj.idx - 1;
        const s = obj.s;
        if (idx < 0) {
            idx = s.length - 1;
        }
        return this.offsetBack(idx, s);
    }

    private findString(c: string): {s: string, idx: number} {
        const input = (c.charCodeAt(0) - ASCII_A + this.offset) % Z_VAL;
        const perms = PERMS.get(this.rotarType);
        const target = String.fromCharCode(input + ASCII_A);
        for (const i in perms) {
            const s = perms[i];
            let idx = s.indexOf(target);
            if (idx > -1)  {
                return {s, idx};
            }
        }
        throw new Error("Character not found in perm");
    }

    private offsetBack(idx: number, s: string): string {
        let res = s.charCodeAt(idx) - ASCII_A - this.offset;
        if (res < 0) {
            res = Z_VAL + res;
        }
        return String.fromCharCode(res + ASCII_A);
    }


    isInNotch(): boolean {
        const notches = NOTCH.get(this.rotarType);
        const c = String.fromCharCode(this.offset + ASCII_A);
        return notches.includes(c);
    }
}


export class Rotor extends BaseRotor {

    constructor(position: string, rotarType: string) {
        super();

        if (!ROTOR_TYPES.includes(rotarType)) {
            throw new Error('Invalid Rotar Type Input');
        }

        if (!ALPHA_CAP.test(position)) {
            throw new Error('Invalid Position');
        }

        this.offset = position.charCodeAt(0) - ASCII_A;
        this.rotarType = rotarType;
    }

}

export class Reflector extends BaseRotor {
    constructor(position: string, reflectorType: string) {
        super();

        if (!REFLECTOR_TYPES.includes(reflectorType)) {
            throw new Error('Not a viable reflector type');
        }

        if (!ALPHA_CAP.test(position)) {
            throw new Error('Invalid Position');
        }

        this.offset = position.charCodeAt(0) - ASCII_A;
        this.rotarType = reflectorType;
    }

    advance() {}
    isInNotch(): boolean {
        return false;
    }
}
