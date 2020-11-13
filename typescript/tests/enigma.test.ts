import {Enigma} from '../src/enigma';
import {expect} from 'chai';

describe('engima', function() {
    it('fails on bad inputs', function() {
        expect(() => {new Enigma('C', 'VII', 'VIII', 'I', 'acceleration mode')})
            .to.throw(Error, 'Config must be 4 [A-Z]');

        expect(() => {new Enigma('C', 'VII', 'VII', 'I', 'AXEL')})
            .to.throw(Error, 'Unable to reuse same Rotor');

        expect(() => {new Enigma('C', 'VII', 'I', 'I', 'AXEL')})
            .to.throw(Error, 'Unable to reuse same Rotor');
    });

    it('encrypts', function() {
        const enigma = new Enigma('B', 'III', 'IV', 'I', 'AXLE');

        expect(enigma.encrypt('FROM his shoulder Hiawatha'))
            .to.equal('HYIHL BKOML IUYDC MPPSF SZW');
    });
});
