from http.server import SimpleHTTPRequestHandler, HTTPServer

class ExploitHandler(SimpleHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.send_header('Content-type', 'text/plain')
        self.end_headers()
        self.wfile.write(b"Exploit triggered: attempted JNDI lookup")

if __name__ == '__main__':
    print("Malicious LDAP server running on port 1389...")
    server = HTTPServer(('0.0.0.0', 1389), ExploitHandler)
    server.serve_forever()