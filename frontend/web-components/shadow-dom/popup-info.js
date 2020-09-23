class PopUpInfo extends HTMLElement {
    constructor() {
        super();
        console.log(this);
        // shadow root
        const shadow = this.attachShadow({ mode: 'open' });

        // spans
        const wrapper = document.createElement('span');
        wrapper.setAttribute('class', 'wrapper');
        const icon = document.createElement('span');
        icon.setAttribute('class', 'icon');
        icon.setAttribute('tabindex', 0);
        const info = document.createElement('span');
        info.setAttribute('class', 'info');

        // text attr
        const text = this.getAttribute('text');
        info.textContent = text;

        // insert icon
        let imgUrl;
        if (this.hasAttribute('img')) {
            imgUrl = this.getAttribute('img');
        } else {
            imgUrl = 'default.png';
        }
        const img = document.createElement('img');
        img.src = imgUrl;
        icon.appendChild(img);

        // styling
        const style = document.createElement('style');
        style.textContent = `
            .wrapper {
                position: relative;
            }

            .info {
                font-size: 0.8rem;
                width: 200px;
                display: inline-block;
                border: 1px solid black;
                padding: 10px;
                background: white;
                border-radius: 10px;
                opacity: 0;
                transition: 0.6s all;
                position: absolute;
                bottom: 20px;
                left: 10px;
                z-index: 3;
            }

            img {
                width: 1.2rem;
            }

            .icon:hover + .info, .icon:focus + .info {
                opacity: 1;
            }
        `;

        // mounting
        shadow.appendChild(style);
        shadow.appendChild(wrapper);
        wrapper.appendChild(icon);
        wrapper.appendChild(info);
    }
}

// Define the new element
customElements.define('popup-info', PopUpInfo);