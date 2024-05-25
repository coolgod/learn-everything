function throttle(func, wait) {
  let isWaiting = false;
  let lastArgs = null;

  function throttled() {
    const [context, args] = [this, arguments];

    if (!isWaiting) {
      func.apply(context, args);
      isWaiting = true;
      const timeout = () => setTimeout(() => {
        isWaiting = false;
        if (lastArgs) {
          func.apply(context, lastArgs);
          isWaiting = true;
          lastArgs = null;
          timeout();
        }
      }, wait);
      timeout();
    } else {
      lastArgs = args;
    }
  }

  return throttled;
}



// with options
function throttle(func, wait, option = {leading: true, trailing: true}) {
  let isWaiting = false;
  let lastArgs = null;
  let lastContext = null;
  const { leading, trailing } = option;

  function throttled() {
    const [context, args] = [this, arguments];

    if (!isWaiting) {
      if (leading) {
        func.apply(context, args);
      }
      isWaiting = true;
      const timeout = () => setTimeout(() => {
        if (lastArgs && trailing) {
          func.apply(lastContext, lastArgs);
          isWaiting = true;
          lastArgs = null;
          lastContext = null;
          timeout();
        } else {
          isWaiting = false;
          // it doesn't matter we don't reset lastArgs/lastContext here
          // because the trailing edge will never be invoked
          // and the leading edge will always invoke the latest context and args
        }
      }, wait);
      timeout();
    } else {
      lastArgs = args;
      lastContext = context;
    }
  }

  return throttled;
}
