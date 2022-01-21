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
