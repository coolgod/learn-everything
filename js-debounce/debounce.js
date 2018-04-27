function debounce(
    fn,
    ms,
    options={ 
        leading: false,
     }) {
        const { leading } = options;
        // internal timer
        let timer = null;
        return function() {
            if (!timer) {
                if (leading) {
                    fn();
                    timer = setTimeoutout(() => {
                        timer = null;
                    }, ms);
                } else {
                    timer = setTimeout(() => {
                        fn();
                        timer = null;
                    }, ms);
                }
            }
        };
}

module.exports = debounce;