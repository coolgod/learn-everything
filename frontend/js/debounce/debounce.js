function debounce(func, wait, option = {leading: false, trailing: true}) {
  let timerId = null;
  let lastArgs = null;
  const { leading, trailing } = option;

  function debounced() {
    const [context, args] = [this, arguments];

    function delayed() {
      // invoke at trailing edge only if there is `lastArgs` scheduled
      if (lastArgs && trailing) {
        func.apply(context, lastArgs);
      }
      lastArgs = null;
      timerId = null;
    }

    // invoke at leading edge
    if (!timerId && leading) {
      func.apply(context, args);
    } else {
      lastArgs = args;
    }
    // always reset timer
    clearTimeout(timerId);
    // schedule to run at trailing edge
    timerId = setTimeout(delayed, wait);
  }
  return debounced;
}

module.exports = debounce;
