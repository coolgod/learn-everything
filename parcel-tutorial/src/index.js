import { bro } from './bro';
import './styles/main.scss';
import ScrollMagic from 'scrollmagic';

// https://www.npmjs.com/package/scrollmagic
const controller = new ScrollMagic.Controller();

new ScrollMagic.Scene({
    duration: 500,
    offset: 50
})
.setPin("body")
.addTo(controller);

document.querySelector('h1').textContent = bro("How's it going");