# benjals

## Usage

Heroku requires the DB connection to be specified with an environment variable DATABASE_URL

IntelliJ doesn't pick up environment variables unless you start is like below:

```
sea-fastr-m:etc fastr$ export DATABASE_URL=postgresql://localhost:5432/benjals
sea-fastr-m:etc fastr$ open -a /Applications/IntelliJ\ IDEA\ 12.app/
```

To run the app (requires Leiningen):

```
lein run
```

## License

Copyright © 2013 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
