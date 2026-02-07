#!/usr/bin/env python3

import urllib.request
from concurrent.futures import ThreadPoolExecutor, as_completed
import time
from datetime import datetime


def send_request(request_id):
    url = "http://localhost:3000"
    start_time = time.time()

    try:
        with urllib.request.urlopen(url, timeout=10) as response:
            status_code = response.status
            content_length = len(response.read())
            elapsed = time.time() - start_time

            return {
                'id': request_id,
                'status': status_code,
                'content_length': content_length,
                'elapsed': elapsed,
                'success': True,
                'error': None
            }
    except Exception as e:
        elapsed = time.time() - start_time
        return {
            'id': request_id,
            'status': None,
            'content_length': 0,
            'elapsed': elapsed,
            'success': False,
            'error': str(e)
        }


def main():
    num_requests = 10

    print(f"Starting {num_requests} concurrent HTTP GET requests to localhost:3000")
    print(f"Timestamp: {datetime.now().strftime('%Y-%m-%d %H:%M:%S')}")
    print("-" * 80)

    overall_start = time.time()
    results = []

    with ThreadPoolExecutor(max_workers=num_requests) as executor:
        futures = {executor.submit(send_request, i): i for i in range(1, num_requests + 1)}

        for future in as_completed(futures):
            result = future.result()
            results.append(result)

            status = "✓" if result['success'] else "✗"
            print(f"{status} Request {result['id']}: "
                  f"Status={result['status']}, "
                  f"Size={result['content_length']} bytes, "
                  f"Time={result['elapsed']:.3f}s"
                  + (f", Error={result['error']}" if result['error'] else ""))

    overall_elapsed = time.time() - overall_start

    print("-" * 80)
    print("\nSummary:")
    successful = sum(1 for r in results if r['success'])
    failed = num_requests - successful
    avg_time = sum(r['elapsed'] for r in results) / num_requests

    print(f"Total requests: {num_requests}")
    print(f"Successful: {successful}")
    print(f"Failed: {failed}")
    print(f"Average response time: {avg_time:.3f}s")
    print(f"Total execution time: {overall_elapsed:.3f}s")


if __name__ == "__main__":
    main()