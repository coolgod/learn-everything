function debounce(
    fn,
    ms,
    options={ 
        leading: false,
     }) {
        const { leading } = options;
        // internal timer
        let timeout = null;
        return function(...args) {
            const context = this;

            // always cancel previous schedule
            // upon receiving consecutive calls
            if (timeout) {
                clearTimeout(timeout);
            }

            // if not leading schedule execution at a later time
            if (!leading) {
                timeout = setTimeout(() => fn.apply(context, args), ms);
            }
            
            // if leading
            if (leading) {
                // if timeout is null, execute immediately
                if (!timeout) {
                    fn.apply(context, args);
                }

                // create/update the timeout
                // and schedule to reset it to null at a later time
                timeout = setTimeout(() => {
                    timeout = null;
                }, ms);
            }
        };
}

module.exports = debounce;